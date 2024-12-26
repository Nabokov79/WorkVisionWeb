package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.ResponseElementHardnessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.UltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface HardnessMeasurementMapper {

    @Mapping(source = "measurementDto.equipmentId", target = "equipmentId")
    @Mapping(source = "measurementDto.elementId", target = "elementId")
    @Mapping(source = "measurementDto.partElementId", target = "partElementId")
    HardnessMeasurement mapToHardnessMeasurement(HardnessMeasurementDto measurementDto
                                               , EquipmentDto equipment
                                               , LocalDate measurementDate);

    @Mapping(target = "equipmentId", ignore = true)
    @Mapping(target = "elementId", ignore = true)
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    @Mapping(source = "averageMeasurementValue", target = "measurementValue")
    void mapToUpdateHardnessMeasurement(@MappingTarget HardnessMeasurement measurement
            , int averageMeasurementValue
            , LocalDate measurementDate);

    ResponseElementHardnessMeasurementDto mapToResponseHardnessMeasurementDto(HardnessMeasurement measurement);

    void mapWithDataCalculation(@MappingTarget HardnessMeasurement measurement
                                             , boolean noStandard
                                             , boolean acceptable
                                             , boolean invalid
                                             , String measurementStatus);

    UltrasonicResidualThicknessMeasurementDto mepToUltrasonicThicknessMeasurementDto(HardnessMeasurement measurement);
}