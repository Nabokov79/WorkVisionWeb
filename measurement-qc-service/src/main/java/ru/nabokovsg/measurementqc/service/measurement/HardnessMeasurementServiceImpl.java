package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.client.MeasurementQCClient;
import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableMetalHardnessDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.exceptions.BadRequestException;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.measurement.HardnessMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;
import ru.nabokovsg.measurementqc.repository.measurement.HardnessMeasurementRepository;
import ru.nabokovsg.measurementqc.service.сalculation.CalculationHardnessMeasurementService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HardnessMeasurementServiceImpl implements HardnessMeasurementService {

    private final HardnessMeasurementRepository repository;
    private final HardnessMeasurementMapper mapper;
    private final CalculationHardnessMeasurementService calculationService;
    private final UltrasonicResidualThicknessMeasurementService residualThicknessMeasurementService;
    private final MeasurementQCClient client;

    @Override
    public ResponseElementHardnessMeasurementDto save(HardnessMeasurementDto measurementDto) {
        HardnessMeasurement measurement = getDuplicate(measurementDto);
        EquipmentDto equipment = client.getEquipmentData(measurementDto.getElementId(), measurementDto.getPartElementId());
        AcceptableMetalHardnessDto acceptableHardness = client.getAcceptableMetalHardness(equipment);
        if (measurement == null) {
            measurement = mapper.mapToHardnessMeasurement(measurementDto, equipment, LocalDate.now());
        } else {
            update(measurement, measurementDto);
        }
        validateMeasurement(equipment, measurement, acceptableHardness);
        calculationService.setMeasurementStatus(measurement, acceptableHardness);
        return mapper.mapToResponseHardnessMeasurementDto(repository.save(measurement));
    }

    @Override
    public ResponseElementHardnessMeasurementDto get(Long id) {
        return mapper.mapToResponseHardnessMeasurementDto(
                repository.findById(id).orElseThrow(() ->
                        new NotFoundException(
                                String.format("Hardness measurement result with id=%s not found", id)))
        );
    }

    private void update(HardnessMeasurement measurement, HardnessMeasurementDto measurementDto) {
        LocalDate date = LocalDate.now();
        if (measurement.getMeasurementDate().equals(date)) {
             mapper.mapToUpdateHardnessMeasurement(
                                    measurement
                                  , calculationService.getAverageMeasurementValue(measurement.getMeasurementValue()
                                                                                , measurementDto.getMeasurementValue())
                                 , date);
        }
    }

    @Override
    public List<ResponseElementHardnessMeasurementDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                        .stream()
                        .map(mapper::mapToResponseHardnessMeasurementDto)
                        .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Hardness measurement with id%s not found delete", id));
    }

    private HardnessMeasurement getDuplicate(HardnessMeasurementDto measurementDto) {
        if (measurementDto.getPartElementId() != null) {
            return repository.findByEquipmentIdAndElementIdAndPartElementIdAndMeasurementNumber(
                                                                                  measurementDto.getEquipmentId()
                                                                                , measurementDto.getElementId()
                                                                                , measurementDto.getPartElementId()
                                                                                , measurementDto.getMeasurementNumber()
            );
        }
        return repository.findByEquipmentIdAndElementIdAndMeasurementNumber(measurementDto.getEquipmentId()
                                                                          , measurementDto.getElementId()
                                                                          , measurementDto.getMeasurementNumber());
    }

    private void validateMeasurement(EquipmentDto equipment
                                   , HardnessMeasurement measurement
                                   , AcceptableMetalHardnessDto acceptableHardness) {
        UltrasonicResidualThicknessMeasurement residualThickness =
                residualThicknessMeasurementService.getByPredicateData(
                        mapper.mepToUltrasonicThicknessMeasurementDto(measurement));
        if (equipment.getPartElementId() == null) {
            if ((equipment.getMinDiameter() <= acceptableHardness.getMinAcceptableDiameter())
                    || (residualThickness.getMinMeasurementValue() <= acceptableHardness.getMinAcceptableThickness())) {
                throw new BadRequestException(
                        String.format("The values of the element's standard size and residual thickness"
                                        + " are below acceptable values, standardSize=%s, residualThickness=%s"
                                , measurement.getStandardSize()
                                , residualThickness.getResidualThickness()));
            }
        }
    }
}