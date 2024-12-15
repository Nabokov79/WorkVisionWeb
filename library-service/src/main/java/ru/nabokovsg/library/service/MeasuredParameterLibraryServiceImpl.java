package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.measurementParameterLibrary.NewMeasurementParameterLibraryDto;
import ru.nabokovsg.library.dto.measurementParameterLibrary.UpdateMeasurementParameterLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.mapper.MeasuredParameterLibraryMapper;
import ru.nabokovsg.library.model.MeasurementParameterLibrary;
import ru.nabokovsg.library.model.MeasurementParameterType;
import ru.nabokovsg.library.model.TypeMeasuredParameterBuilder;
import ru.nabokovsg.library.model.UnitMeasurementType;
import ru.nabokovsg.library.repository.MeasuredParameterLibraryRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasuredParameterLibraryServiceImpl implements MeasuredParameterLibraryService {

    private final MeasuredParameterLibraryRepository repository;
    private final MeasuredParameterLibraryMapper mapper;

    @Override
    public Set<MeasurementParameterLibrary> save(TypeMeasuredParameterBuilder builder
                                                   , List<NewMeasurementParameterLibraryDto> measuredParameters) {
        List<MeasurementParameterLibrary> parametersLibrary = measuredParameters.stream()
                .peek(parameter -> validMaxAllowedValue(parameter.getMaxAllowedValue()))
                .map(parameter -> mapper.mapToMeasuredParameter(MeasurementParameterType.valueOf(parameter.getParameterName()).label
                            , parameter.getMaxAllowedValue()
                            , UnitMeasurementType.valueOf(parameter.getUnitMeasurement()).label))
                .collect(Collectors.toCollection(ArrayList::new));
        return new HashSet<>(repository.saveAll(map(builder, parametersLibrary)));
    }

    @Override
    public Set<MeasurementParameterLibrary> update(TypeMeasuredParameterBuilder builder
                                                     , List<UpdateMeasurementParameterLibraryDto> measuredParameters) {
        measuredParameters.forEach(parameter -> validMaxAllowedValue(parameter.getMaxAllowedValue()));
        return new HashSet<>(repository.saveAll(
                getParameters(builder).stream()
                                      .map(parameter -> mapper.mapToUpdateMeasuredParameter(parameter
                                                  , MeasurementParameterType.valueOf(parameter.getParameterName()).label
                                                  , parameter.getMaxAllowedValue()
                                                  , UnitMeasurementType.valueOf(parameter.getUnitMeasurement()).label))
                        .toList()));
    }

    @Override
    public void delete(Set<MeasurementParameterLibrary> measuredParameters) {
        repository.deleteAllById(measuredParameters.stream().map(MeasurementParameterLibrary::getId).toList());
    }

    private List<MeasurementParameterLibrary> map(TypeMeasuredParameterBuilder builder
                                                    , List<MeasurementParameterLibrary> parameters) {
        switch (builder.getLibraryDataType()) {
            case DEFECT -> {
                return parameters.stream()
                                 .map(parameter -> mapper.mapWithDefect(parameter, builder.getDefect()))
                                 .collect(Collectors.toList());
            }
            case REPAIR -> {
                return parameters.stream()
                                 .map(parameter -> mapper.mapWithRepair(parameter, builder.getRepair()))
                                 .collect(Collectors.toList());
            }
            default ->
                    throw new BadRequestException(
                            String.format("Incorrect library data type =%s", builder.getLibraryDataType()));
        }
    }

    private Set<MeasurementParameterLibrary> getParameters(TypeMeasuredParameterBuilder builder) {
        switch (builder.getLibraryDataType()) {
            case DEFECT -> {
                return builder.getDefect().getMeasuredParameters();
            }
            case REPAIR -> {
                return builder.getRepair().getMeasuredParameters();
            }
            default ->
                    throw new BadRequestException(
                            String.format("Incorrect library data type =%s", builder.getLibraryDataType()));
        }
    }

    private void validMaxAllowedValue(Double maxAllowedValue) {
        if (maxAllowedValue != null && maxAllowedValue <= 0) {
            throw new BadRequestException
                    (String.format("Maximum allowed value can only be positive, maxAllowedValue=%s", maxAllowedValue));
        }
    }
}