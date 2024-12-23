package ru.nabokovsg.measurementqc.mapper.qualityControl;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.UpdateVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;

@Mapper(componentModel = "spring")
public interface VisualMeasurementControlMapper {

    VisualMeasurementControl mapToVisualMeasurementControl(NewVisualMeasurementControlDto defectDto);

    UpdateVisualMeasurementControlDto mapToUpdateVisualMeasurementControlDto(NewVisualMeasurementControlDto defectDto);

    ResponseVisualMeasurementControlDto mapToResponseVisualMeasurementControlDto(VisualMeasurementControl defect);

    void mapToUpdateDefectName(@MappingTarget VisualMeasurementControl defect, String defectName);

    void mapToPositiveQualityAssessment(@MappingTarget VisualMeasurementControl defect
                                                                  , String defectName
                                                                  , String coordinates
                                                                  , String qualityAssessment);
}