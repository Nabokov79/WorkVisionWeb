package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.mapper.measurement.IdentifiedDefectMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
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
    private final DefectLibraryService defectLibraryService;
    private final EquipmentElementService equipmentElementService;
    private final MeasuredParameterService measuredParameterService;
    private final MeasurementParameterValidateService validateService;
    private final CalculationIdentifiedDefectMeasurementService measurementResultsUpdateService;
    private final CalculationMeasuredResidualThicknessService calculationMeasuredResidualThicknessService;

    @Override
    public ResponseIdentifiedDefectMeasurementDto save(NewIdentifiedDefectMeasurementDto identifiedDefectDto) {
        IdentifiedDefectMeasurement identifiedDefect = mapper.mapToIdentifiedDefectMeasurement(identifiedDefectDto);
        DefectLibrary typeDefectLibrary = defectLibraryService.getById(identifiedDefectDto.getDefectId());
        identifiedDefect = validateService.searchIdentifiedDefectMeasurementDuplicate(identifiedDefect
                                                                                , getAllByPredicate(identifiedDefect));
        if (identifiedDefect.getId() == null) {
            mapper.mapWitEquipmentDiagnosedData(identifiedDefect
                    , typeDefectLibrary
                    , equipmentElementService.getEquipmentDiagnosedData(identifiedDefectDto.getElementId()));
            identifiedDefect = repository.save(identifiedDefect);
            identifiedDefect.setMeasuredParameters(measuredParameterService.save(
                    new ParameterMeasurementBuilder.Builder()
                            .libraryDataType(LibraryDataType.IDENTIFIED_DEFECT)
                            .identifiedDefect(identifiedDefect)
                            .measurementParameterLibraries(typeDefectLibrary.getMeasuredParameters())
                            .newMeasuredParameters(identifiedDefectDto.getMeasuredParameters())
                            .build()));
        } else {
            identifiedDefect.setMeasuredParameters(
                    measuredParameterService.update(identifiedDefect.getMeasuredParameters(), null));
        }
        calculate(typeDefectLibrary
                , identifiedDefect
                , identifiedDefectDto.getMeasuredParameters()
                                    .stream()
                                    .collect(Collectors.toMap(NewMeasuredParameterDto::getParameterId
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
        calculate(defectLibraryService.getById(duplicate.getDefectId())
                , identifiedDefect
                , identifiedDefectDto.getMeasuredParameters()
                                     .stream()
                                     .collect(Collectors.toMap(UpdateMeasuredParameterDto::getParameterId
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
        IdentifiedDefectMeasurement identifiedDefect = getById(id);
        measuredParameterService.deleteAll(LibraryDataType.IDENTIFIED_DEFECT, id);
        repository.deleteById(id);
        calculationMeasuredResidualThicknessService.updateResidualThicknessMeasurementsEquipmentElements(identifiedDefect);
    }

    private IdentifiedDefectMeasurement getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Identified defect with id=%s not found", id)));
    }

    private Set<IdentifiedDefectMeasurement> getAllByPredicate(IdentifiedDefectMeasurement identifiedDefect) {
        if (identifiedDefect.getPartElementId() != null) {
            return repository.findAllByEquipmentIdAndElementIdAndPartElementIdAndDefectId(identifiedDefect.getEquipmentId()
                    , identifiedDefect.getElementId()
                    , identifiedDefect.getPartElementId()
                    , identifiedDefect.getDefectId());
        } else {
            return repository.findAllByEquipmentIdAndElementIdAndDefectId(identifiedDefect.getEquipmentId()
                    , identifiedDefect.getElementId()
                    , identifiedDefect.getDefectId());
        }
    }


    private void calculate(DefectLibrary typeDefectLibrary
                         , IdentifiedDefectMeasurement identifiedDefect
                         , Map<Long, Double> measurementValues) {
        if (typeDefectLibrary.getUseCalculateThickness()) {
            measurementResultsUpdateService.calculateByResidualThickness(identifiedDefect);
            calculationMeasuredResidualThicknessService.updateResidualThicknessMeasurementsEquipmentElements(identifiedDefect);
        } else {
            measurementResultsUpdateService.calculateByMaxAllowedValue(typeDefectLibrary, identifiedDefect, measurementValues);
        }
    }
}