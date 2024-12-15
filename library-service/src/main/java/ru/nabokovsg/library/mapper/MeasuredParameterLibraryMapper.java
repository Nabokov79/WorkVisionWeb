package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.MeasurementParameterLibrary;
import ru.nabokovsg.library.model.RepairLibrary;

@Mapper(componentModel = "spring")
public interface MeasuredParameterLibraryMapper {

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "maxAllowedValue", target = "maxAllowedValue")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(target = "elementRepair", ignore = true)
    @Mapping(target = "id", ignore = true)
    MeasurementParameterLibrary mapToMeasuredParameter(String parameterName, Double maxAllowedValue, String unitMeasurement);

    @Mapping(source = "parameterName", target = "parameterName")
    @Mapping(source = "maxAllowedValue", target = "maxAllowedValue")
    @Mapping(source = "unitMeasurement", target = "unitMeasurement")
    @Mapping(target = "elementRepair", ignore = true)
    @Mapping(target = "id", ignore = true)
    MeasurementParameterLibrary mapToUpdateMeasuredParameter(@MappingTarget MeasurementParameterLibrary parameter
                                                                              , String parameterName
                                                                              , Double maxAllowedValue
                                                                              , String unitMeasurement);

    @Mapping(source = "defect", target = "defect")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "elementRepair", ignore = true)
    MeasurementParameterLibrary mapWithDefect(@MappingTarget MeasurementParameterLibrary parameter
                                                                , DefectLibrary defect);

    @Mapping(source = "elementRepair", target = "elementRepair")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "defect", ignore = true)
    MeasurementParameterLibrary mapWithRepair(@MappingTarget MeasurementParameterLibrary parameter
                                                              , RepairLibrary elementRepair);
}