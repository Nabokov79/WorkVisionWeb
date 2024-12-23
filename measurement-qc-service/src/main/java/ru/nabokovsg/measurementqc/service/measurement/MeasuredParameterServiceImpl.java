package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.equipmentDiagnosedQCL.exceptions.BadRequestException;
import ru.nabokovsg.equipmentDiagnosedQCL.mapper.measurement.MeasuredParameterMapper;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.LibraryDataType;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.MeasurementParameterLibrary;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.MeasuredParameter;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.ParameterMeasurementBuilder;
import ru.nabokovsg.equipmentDiagnosedQCL.model.qualityControl.VisualMeasurementControl;
import ru.nabokovsg.equipmentDiagnosedQCL.repository.measurement.MeasuredParameterRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasuredParameterServiceImpl implements MeasuredParameterService {

    private final MeasuredParameterRepository repository;
    private final MeasuredParameterMapper mapper;

    @Override
    public Set<MeasuredParameter> save(ParameterMeasurementBuilder builder) {
        return new HashSet<>(repository.saveAll(map(builder)));
    }

    @Override
    public Set<MeasuredParameter> update(Set<MeasuredParameter> measuredParameters
                                       , List<UpdateMeasuredParameterDto> measuredParametersDto) {
        if (measuredParametersDto != null) {
            Map<Long, Double> values = measuredParametersDto.stream()
                    .collect(Collectors.toMap(UpdateMeasuredParameterDto::getId, UpdateMeasuredParameterDto::getValue));
            measuredParameters.forEach(parameter -> mapper.mapToUpdateMeasuredParameter(parameter, values.get(parameter.getId())));
        }
        return new HashSet<>(repository.saveAll(measuredParameters));
    }

    @Override
    public void deleteAll(LibraryDataType libraryDataType, Long id) {
        switch (libraryDataType) {
            case IDENTIFIED_DEFECT -> repository.deleteAllByIdentifiedDefectId(id);
            case REPAIR -> repository.deleteAllByCompletedRepairId(id);
            case DEFECT -> repository.deleteAllByVisualMeasurementControlId(id);
        }
    }

    private List<MeasuredParameter> map(ParameterMeasurementBuilder builder) {
        Map<Long, MeasurementParameterLibrary> measuredParametersLibraries = builder.getMeasurementParameterLibraries()
                .stream()
                .collect(Collectors.toMap(MeasurementParameterLibrary::getId, parameter -> parameter));
        switch (builder.getLibraryDataType()) {
            case IDENTIFIED_DEFECT -> {
                return mapWithIdentifiedDefect(builder.getIdentifiedDefect()
                                             , measuredParametersLibraries
                                             , builder.getNewMeasuredParameters());
            }
            case REPAIR -> {
                return mapWithCompletedRepair(builder.getCompletedRepair()
                                            , measuredParametersLibraries
                                            , builder.getNewMeasuredParameters());
            }
            case DEFECT -> {
                return mapWithDefect(builder.getDefect()
                                   , measuredParametersLibraries
                                   , builder.getUpdateMeasuredParameters());
            }
            default -> throw new BadRequestException(
                    String.format("Parameter mapping is not supported, type=%s", builder.getLibraryDataType()));
        }
    }

    private List<MeasuredParameter> mapWithIdentifiedDefect(IdentifiedDefectMeasurement identifiedDefect
                                              , Map<Long, MeasurementParameterLibrary> measuredParametersLibraries
                                              , List<NewMeasuredParameterDto> measuredParameters) {
        return  measuredParameters.stream()
                                   .map(parameter -> mapper.mapWithIdentifiedDefect(
                                                             measuredParametersLibraries.get(parameter.getParameterId())
                                                           , parameter.getValue()
                                                           , identifiedDefect))
                                  .toList();
    }

    private List<MeasuredParameter> mapWithCompletedRepair(CompletedRepairMeasurement completedRepair
            , Map<Long, MeasurementParameterLibrary> measuredParametersLibraries
            , List<NewMeasuredParameterDto> measuredParameters) {
        return measuredParameters.stream()
                                 .map(parameter -> mapper.mapWithCompletedRepair(
                                                           measuredParametersLibraries.get(parameter.getParameterId())
                                                         , parameter.getValue()
                                                         , completedRepair))
                                 .toList();
    }

    private List<MeasuredParameter> mapWithDefect(VisualMeasurementControl defect
            , Map<Long, MeasurementParameterLibrary> measuredParametersLibraries
            , List<UpdateMeasuredParameterDto> measuredParameters) {
        return measuredParameters.stream()
                .map(parameter -> mapper.mapWithDefect(measuredParametersLibraries.get(parameter.getParameterId())
                                                    , parameter.getValue()
                                                    , defect))
                .toList();
    }
}