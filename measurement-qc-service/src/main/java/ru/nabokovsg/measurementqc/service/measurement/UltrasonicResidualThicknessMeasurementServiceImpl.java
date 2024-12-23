package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.ResponseUltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.UltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.measurement.UltrasonicResidualThicknessMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;
import ru.nabokovsg.measurementqc.repository.measurement.UltrasonicResidualThicknessMeasurementRepository;
import ru.nabokovsg.measurementqc.service.сalculation.CalculationIdentifiedDefectMeasurementService;
import ru.nabokovsg.measurementqc.service.сalculation.CalculationMeasuredResidualThicknessService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UltrasonicResidualThicknessMeasurementServiceImpl implements UltrasonicResidualThicknessMeasurementService {

    private final UltrasonicResidualThicknessMeasurementRepository repository;
    private final UltrasonicResidualThicknessMeasurementMapper mapper;
    private final CalculationMeasuredResidualThicknessService calculationService;
    private final AcceptableResidualThicknessService acceptableThicknessService;
    private final EquipmentElementService equipmentElementService;
    private final CalculationIdentifiedDefectMeasurementService measurementResultsUpdateService;

    @Override
    public ResponseUltrasonicResidualThicknessMeasurementDto save(UltrasonicResidualThicknessMeasurementDto measurementDto) {
        UltrasonicResidualThicknessMeasurement measurement = getByPredicateData(measurementDto);
        EquipmentDiagnosedData equipmentData
                                     = equipmentElementService.getEquipmentDiagnosedData(measurementDto.getElementId());
        AcceptableResidualThickness acceptableThickness
                                               = acceptableThicknessService.getByEquipmentDiagnosedData(equipmentData);
        if (measurement == null) {
            measurement = mapper.mapToUltrasonicResidualThicknessMeasurement(equipmentData
                    , measurementDto
                    , LocalDate.now());
        } else {
            update(measurement, measurementDto);
        }
        calculationService.calculation(measurement, equipmentData, acceptableThickness);
        measurementResultsUpdateService.updateUnacceptableByResidualThickness(measurement, acceptableThickness);
        return mapper.mapToResponseUltrasonicThicknessMeasurementDto(repository.save(measurement));
    }

    private void update(UltrasonicResidualThicknessMeasurement measurement
            , UltrasonicResidualThicknessMeasurementDto measurementDto) {
        LocalDate date = LocalDate.now();
        if (measurement.getMeasurementDate().equals(date)) {
            measurement.setMinMeasurementValue(Math.min(measurement.getMinMeasurementValue()
                    , measurementDto.getMinMeasurementValue()));
            measurement.setMaxMeasurementValue(Math.max(measurement.getMaxMeasurementValue()
                    , measurementDto.getMaxMeasurementValue()));
        } else {
            mapper.mapToUpdateUltrasonicResidualThicknessMeasurement(measurement, measurementDto, date);
        }
    }

    @Override
    public ResponseUltrasonicResidualThicknessMeasurementDto get(Long id) {
        return mapper.mapToResponseUltrasonicThicknessMeasurementDto(getById(id));
    }

    @Override
    public List<ResponseUltrasonicResidualThicknessMeasurementDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentIdOrderByMeasurementNumberDesc(equipmentId)
                .stream()
                .map(mapper::mapToResponseUltrasonicThicknessMeasurementDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        UltrasonicResidualThicknessMeasurement measurement = getById(id);
        repository.deleteById(id);
        EquipmentDiagnosedData equipmentData =
                                        equipmentElementService.getEquipmentDiagnosedData(measurement.getElementId());
        measurementResultsUpdateService.updateUnacceptableByResidualThickness(measurement
                                               , acceptableThicknessService.getByEquipmentDiagnosedData(equipmentData));
    }

    @Override
    public UltrasonicResidualThicknessMeasurement getByPredicateData(
            UltrasonicResidualThicknessMeasurementDto measurementDto) {
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

    public UltrasonicResidualThicknessMeasurement getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(
                        String.format("Ultrasonic residual thickness measurement result with id=%s not found", id))
        );
    }
}