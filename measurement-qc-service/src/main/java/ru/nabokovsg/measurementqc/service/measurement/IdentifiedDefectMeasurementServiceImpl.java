package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.client.MeasurementQCClient;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.measurement.IdentifiedDefectMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.LibraryDataType;
import ru.nabokovsg.measurementqc.model.measurement.ParameterMeasurementBuilder;
import ru.nabokovsg.measurementqc.repository.measurement.IdentifiedDefectMeasurementRepository;
import ru.nabokovsg.measurementqc.service.сalculation.CalculationIdentifiedDefectMeasurementService;
import ru.nabokovsg.measurementqc.service.сalculation.CalculationMeasuredResidualThicknessService;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IdentifiedDefectMeasurementServiceImpl implements IdentifiedDefectMeasurementService {

    private final IdentifiedDefectMeasurementRepository repository;
    private final IdentifiedDefectMeasurementMapper mapper;
    private final MeasuredParameterService measuredParameterService;
    private final MeasurementParameterValidateService validateService;
    private final CalculationIdentifiedDefectMeasurementService measurementResultsUpdateService;
    private final CalculationMeasuredResidualThicknessService calculationMeasuredResidualThicknessService;
    private final MeasurementQCClient client;

    @Override
    public ResponseIdentifiedDefectMeasurementDto save(NewIdentifiedDefectMeasurementDto identifiedDefectDto) {
        IdentifiedDefectMeasurement identifiedDefect = mapper.mapToIdentifiedDefectMeasurement(identifiedDefectDto);
        identifiedDefect = validateService.searchIdentifiedDefectMeasurementDuplicate(identifiedDefect
                                                                                , getAllByPredicate(identifiedDefect));
        LibraryDto defectLibrary = client.getLibraryData(identifiedDefectDto.getDefectLibraryId(), LibraryDataType.IDENTIFIED_DEFECT);
        if (identifiedDefect.getId() == null) {
            mapper.mapWitEquipmentDiagnosedData(identifiedDefect
                    , defectLibrary
                    , client.getEquipmentData(identifiedDefectDto.getElementId(), identifiedDefectDto.getPartElementId()));
            identifiedDefect = repository.save(identifiedDefect);
            identifiedDefect.setMeasuredParameters(measuredParameterService.save(
                    new ParameterMeasurementBuilder.Builder()
                            .libraryDataType(LibraryDataType.IDENTIFIED_DEFECT)
                            .identifiedDefect(identifiedDefect)
                            .measurementParameterLibraries(defectLibrary.getMeasuredParameters())
                            .newMeasuredParameters(identifiedDefectDto.getMeasuredParameters())
                            .build()));
        } else {
            identifiedDefect.setMeasuredParameters(
                    measuredParameterService.update(identifiedDefect.getMeasuredParameters(), null));
        }
        calculate(defectLibrary
                , identifiedDefect
                , identifiedDefectDto.getMeasuredParameters()
                                    .stream()
                                    .collect(Collectors.toMap(NewMeasuredParameterDto::getParameterLibraryId
                                                            , NewMeasuredParameterDto::getValue)));
        return mapper.mapToResponseIdentifiedDefectDto(identifiedDefect);
    }

    @Override
    public ResponseIdentifiedDefectMeasurementDto update(UpdateIdentifiedDefectMeasurementDto identifiedDefectDto) {
        IdentifiedDefectMeasurement identifiedDefect = getById(identifiedDefectDto.getId());
        IdentifiedDefectMeasurement duplicate = validateService.searchIdentifiedDefectMeasurementDuplicate(
                mapper.mapToUpdateIdentifiedDefectMeasurement(identifiedDefectDto)
                , getAllByPredicate(identifiedDefect));
        identifiedDefect.setMeasuredParameters(measuredParameterService.update(identifiedDefect.getMeasuredParameters()
                , identifiedDefectDto.getMeasuredParameters()));
        if (!Objects.equals(identifiedDefect.getId(), duplicate.getId())) {
            delete(identifiedDefectDto.getId());
        }
        calculate(client.getLibraryData(duplicate.getDefectLibraryId(), LibraryDataType.REPAIR)
                , identifiedDefect
                , identifiedDefectDto.getMeasuredParameters()
                                     .stream()
                                     .collect(Collectors.toMap(UpdateMeasuredParameterDto::getId
                                                             , UpdateMeasuredParameterDto::getValue)));
        return mapper.mapToResponseIdentifiedDefectDto(repository.save(duplicate));
    }

    @Override
    public ResponseIdentifiedDefectMeasurementDto get(Long id) {
        return mapper.mapToResponseIdentifiedDefectDto(getById(id));
    }

    @Override
    public List<ResponseIdentifiedDefectMeasurementDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseIdentifiedDefectDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
       throw new NotFoundException(String.format("Identified defect with id=%s not found for delete", id));
    }

    private IdentifiedDefectMeasurement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Identified defect with id=%s not found", id)));
    }

    private Set<IdentifiedDefectMeasurement> getAllByPredicate(IdentifiedDefectMeasurement identifiedDefect) {
        if (identifiedDefect.getPartElementId() != null) {
            return repository.findAllByEquipmentIdAndElementIdAndPartElementIdAndDefectLibraryId(
                                                                              identifiedDefect.getEquipmentId()
                                                                            , identifiedDefect.getElementId()
                                                                            , identifiedDefect.getPartElementId()
                                                                            , identifiedDefect.getDefectLibraryId());
        } else {
            return repository.findAllByEquipmentIdAndElementIdAndDefectLibraryId(identifiedDefect.getEquipmentId()
                                                                               , identifiedDefect.getElementId()
                                                                               , identifiedDefect.getDefectLibraryId());
        }
    }


    private void calculate(LibraryDto defectLibrary
                         , IdentifiedDefectMeasurement identifiedDefect
                         , Map<Long, Double> measurementValues) {
        if (defectLibrary.getUseCalculateThickness()) {
            EquipmentDto equipment = client.getEquipmentData(identifiedDefect.getElementId()
                                                           , identifiedDefect.getPartElementId());
            AcceptableResidualThicknessDto acceptableThickness = client.getAcceptableResidualThickness(equipment);
            measurementResultsUpdateService.calculateByResidualThickness(identifiedDefect,acceptableThickness);
            calculationMeasuredResidualThicknessService.updateResidualThicknessMeasurementsEquipmentElements(
                                                                     identifiedDefect, equipment, acceptableThickness);
        } else {
            measurementResultsUpdateService.calculateByMaxAllowedValue(defectLibrary, identifiedDefect, measurementValues);
        }
    }
}