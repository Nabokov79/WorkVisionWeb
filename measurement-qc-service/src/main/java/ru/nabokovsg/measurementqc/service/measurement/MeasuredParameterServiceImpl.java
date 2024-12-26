package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.integration.MeasurementParameterLibraryDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.NewMeasuredParameterDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.exceptions.BadRequestException;
import ru.nabokovsg.measurementqc.mapper.measurement.MeasuredParameterMapper;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.MeasuredParameter;
import ru.nabokovsg.measurementqc.model.measurement.ParameterMeasurementBuilder;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;
import ru.nabokovsg.measurementqc.repository.measurement.MeasuredParameterRepository;

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

    private List<MeasuredParameter> map(ParameterMeasurementBuilder builder) {
        Map<Long, MeasurementParameterLibraryDto> measuredParametersLibraries = builder.getMeasurementParameterLibraries()
                .stream()
                .collect(Collectors.toMap(MeasurementParameterLibraryDto::getId, parameter -> parameter));
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
                                   , builder.getNewMeasuredParameters());
            }
            default -> throw new BadRequestException(
                    String.format("Parameter mapping is not supported, type=%s", builder.getLibraryDataType()));
        }
    }

    private List<MeasuredParameter> mapWithIdentifiedDefect(IdentifiedDefectMeasurement identifiedDefect
                                              , Map<Long, MeasurementParameterLibraryDto> measuredParametersLibraries
                                              , List<NewMeasuredParameterDto> measuredParameters) {
        return  measuredParameters.stream()
                                   .map(parameter -> mapper.mapWithIdentifiedDefect(
                                                             measuredParametersLibraries.get(parameter.getParameterLibraryId())
                                                           , parameter.getValue()
                                                           , identifiedDefect))
                                  .toList();
    }

    private List<MeasuredParameter> mapWithCompletedRepair(CompletedRepairMeasurement completedRepair
            , Map<Long, MeasurementParameterLibraryDto> measuredParametersLibraries
            , List<NewMeasuredParameterDto> measuredParameters) {
        return measuredParameters.stream()
                                 .map(parameter -> mapper.mapWithCompletedRepair(
                                                           measuredParametersLibraries.get(parameter.getParameterLibraryId())
                                                         , parameter.getValue()
                                                         , completedRepair))
                                 .toList();
    }

    private List<MeasuredParameter> mapWithDefect(VisualMeasurementControl defect
            , Map<Long, MeasurementParameterLibraryDto> measuredParametersLibraries
            , List<NewMeasuredParameterDto> measuredParameters) {
        return measuredParameters.stream()
                .map(parameter -> mapper.mapWithDefect(measuredParametersLibraries.get(parameter.getParameterLibraryId())
                                                    , parameter.getValue()
                                                    , defect))
                .toList();
    }
}