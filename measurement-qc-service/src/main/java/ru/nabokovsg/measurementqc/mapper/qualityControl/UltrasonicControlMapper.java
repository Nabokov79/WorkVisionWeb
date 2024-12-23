package ru.nabokovsg.measurementqc.mapper.qualityControl;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.NewUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.ResponseUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.UpdateUltrasonicControlDto;
import ru.nabokovsg.measurementqc.model.qualityControl.UltrasonicControl;

@Mapper(componentModel = "spring")
public interface UltrasonicControlMapper {

    UltrasonicControl mapToUltrasonicControl(NewUltrasonicControlDto defectDto);

    UltrasonicControl mapToUpdateUltrasonicControl(UpdateUltrasonicControlDto defectDto);

    ResponseUltrasonicControlDto mapToResponseUltrasonicControlDto(UltrasonicControl defect);

    void mapToPositiveQualityAssessment(@MappingTarget UltrasonicControl defect
                                                     , String descriptionDefect
                                                     , String coordinates);
}