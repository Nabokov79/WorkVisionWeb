package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

@Mapper(componentModel = "spring")
public interface CalculationMeasuredResidualThicknessMapper {

    @Mapping(source = "maxCorrosion", target = "maxCorrosion")
    @Mapping(source = "residualThickness", target = "residualThickness")
    @Mapping(source = "minAcceptableValue", target = "minAcceptableValue")
    void mapWithDataCalculation(@MappingTarget UltrasonicResidualThicknessMeasurement measurement
            , Double maxCorrosion
            , Double residualThickness
            , Double minAcceptableValue);

    @Mapping(source = "measurementStatus", target = "measurementStatus")
    @Mapping(source = "status", target = "status")
    void mapWithMeasurementStatus(@MappingTarget UltrasonicResidualThicknessMeasurement measurement
                                               , String measurementStatus, String status);
}