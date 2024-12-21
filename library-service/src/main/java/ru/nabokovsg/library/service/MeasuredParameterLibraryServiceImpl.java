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

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeasuredParameterLibraryServiceImpl implements MeasuredParameterLibraryService {

    private final MeasuredParameterLibraryRepository repository;
    private final MeasuredParameterLibraryMapper mapper;

    @Override
    public Set<MeasurementParameterLibrary> save(TypeMeasuredParameterBuilder builder
            , List<NewMeasurementParameterLibraryDto> measuredParametersDto) {
        List<MeasurementParameterLibrary> measuredParameters = measuredParametersDto.stream()
                .map(mapper::mapToNewMeasuredParameter)
                .toList();
        create(builder, measuredParameters);
        return new HashSet<>(repository.saveAll(measuredParameters));
    }

    @Override
    public Set<MeasurementParameterLibrary> update(Set<MeasurementParameterLibrary> measuredParametersDb
            , List<UpdateMeasurementParameterLibraryDto> measuredParametersDto) {
        Map<Long, UpdateMeasurementParameterLibraryDto> measuredParameters = measuredParametersDto.stream()
                                        .collect(Collectors.toMap(UpdateMeasurementParameterLibraryDto::getId, p -> p));
        measuredParametersDb.forEach(parameter -> {
                    UpdateMeasurementParameterLibraryDto measuredParameter = measuredParameters.get(parameter.getId());
                    validMaxAllowedValue(parameter.getMaxAllowedValue());
                    mapper.mapToMeasuredParameter(parameter
                            , MeasurementParameterType.valueOf(measuredParameter.getParameterName()).label
                            , UnitMeasurementType.valueOf(measuredParameter.getUnitMeasurement()).label);
                }
        );
        return new HashSet<>(repository.saveAll(measuredParametersDb));
    }

    private void create(TypeMeasuredParameterBuilder builder, List<MeasurementParameterLibrary> measuredParameters) {
        measuredParameters.forEach(parameter -> {
            validMaxAllowedValue(parameter.getMaxAllowedValue());
            mapper.mapToMeasuredParameter(parameter
                    , MeasurementParameterType.valueOf(parameter.getParameterName()).label
                    , UnitMeasurementType.valueOf(parameter.getUnitMeasurement()).label);
            map(builder, parameter);
        });
    }

    private void map(TypeMeasuredParameterBuilder builder, MeasurementParameterLibrary parameter) {
        switch (builder.getLibraryDataType()) {
            case DEFECT -> mapper.mapWithDefect(parameter, builder.getDefect());
            case REPAIR -> mapper.mapWithRepair(parameter, builder.getRepair());
            default -> throw new BadRequestException(
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