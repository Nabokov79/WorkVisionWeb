package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;

@Mapper(componentModel = "spring")
public interface CalculationHardnessMeasurementMapper {

    @Mapping(source = "measurementStatus", target = "measurementStatus")
    @Mapping(source = "status", target = "status")
    void mapWithMeasurementStatus(@MappingTarget HardnessMeasurement measurement
                                               , String measurementStatus
                                               , String status);
}