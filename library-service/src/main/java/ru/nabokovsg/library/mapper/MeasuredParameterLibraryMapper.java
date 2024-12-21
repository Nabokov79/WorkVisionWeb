package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.measurementParameterLibrary.NewMeasurementParameterLibraryDto;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.MeasurementParameterLibrary;
import ru.nabokovsg.library.model.RepairLibrary;

@Mapper(componentModel = "spring")
public interface MeasuredParameterLibraryMapper {

    MeasurementParameterLibrary mapToNewMeasuredParameter(NewMeasurementParameterLibraryDto measuredParametersDto);

    void mapToMeasuredParameter(@MappingTarget MeasurementParameterLibrary parameter
                                                , String parameterName
                                                , String unitMeasurement);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "elementRepair", ignore = true)
    void mapWithDefect(@MappingTarget MeasurementParameterLibrary parameter, DefectLibrary defect);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defect", ignore = true)
    void mapWithRepair(@MappingTarget MeasurementParameterLibrary parameter, RepairLibrary elementRepair);
}