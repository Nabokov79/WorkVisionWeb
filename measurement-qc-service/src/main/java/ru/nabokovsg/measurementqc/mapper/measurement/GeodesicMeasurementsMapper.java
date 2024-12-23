package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.NewGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.ResponseGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.model.measurement.GeodesicMeasurements;

@Mapper(componentModel = "spring")
public interface GeodesicMeasurementsMapper {

    @Mapping(source = "measurementDto.equipmentId", target = "equipmentId")
    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "measurementDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "measurementDto.numberMeasurementLocation", target = "numberMeasurementLocation")
    @Mapping(source = "measurementDto.referencePointValue", target = "referencePointValue")
    @Mapping(source = "measurementDto.controlPointValue", target = "controlPointValue")
    @Mapping(source = "measurementDto.transitionValue", target = "transitionValue")
    @Mapping(target = "id", ignore = true)
    GeodesicMeasurements mapToGeodesicMeasurementsPoint(NewGeodesicMeasurementsDto measurementDto
                                                      , Integer measurementNumber);

    @Mapping(source = "measurementNumber", target = "measurementNumber")
    @Mapping(source = "measurementDto.sequentialNumber", target = "sequentialNumber")
    @Mapping(source = "measurementDto.referencePointValue", target = "referencePointValue")
    @Mapping(source = "measurementDto.controlPointValue", target = "controlPointValue")
    @Mapping(source = "measurementDto.transitionValue", target = "transitionValue")
    @Mapping(target = "id", ignore = true)
    GeodesicMeasurements mapToUpdateGeodesicMeasurementsPoint(@MappingTarget GeodesicMeasurements measurement
                                                                       , NewGeodesicMeasurementsDto measurementDto
                                                                       , Integer measurementNumber);

    ResponseGeodesicMeasurementsDto mapToResponseGeodesicMeasurementsPointDto(GeodesicMeasurements geodesicMeasurement);
}