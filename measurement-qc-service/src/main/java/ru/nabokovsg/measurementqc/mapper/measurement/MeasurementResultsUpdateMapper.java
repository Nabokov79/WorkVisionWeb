package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;

@Mapper(componentModel = "spring")
public interface MeasurementResultsUpdateMapper {

    @Mapping(source = "unacceptable", target = "unacceptable")
    @Mapping(source = "qualityAssessment", target = "qualityAssessment")
    void mapWithQualityAssessment(@MappingTarget IdentifiedDefectMeasurement defect
                                               , Boolean unacceptable
                                               , String qualityAssessment);
}