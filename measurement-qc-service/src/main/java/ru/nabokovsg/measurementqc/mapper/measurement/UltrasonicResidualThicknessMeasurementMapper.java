package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.ResponseUltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.UltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

import java.time.LocalDate;

@Mapper(componentModel = "spring")
public interface UltrasonicResidualThicknessMeasurementMapper {

    @Mapping(source = "measurementDto.equipmentId", target = "equipmentId")
    @Mapping(source = "measurementDto.elementId", target = "elementId")
    @Mapping(source = "measurementDto.partElementId", target = "partElementId")
    UltrasonicResidualThicknessMeasurement mapToUltrasonicResidualThicknessMeasurement(
                                                               EquipmentDiagnosedData equipmentData
                                                             , UltrasonicResidualThicknessMeasurementDto measurementDto
                                                             , LocalDate measurementDate);

    @Mapping(target = "equipmentId", ignore = true)
    @Mapping(target = "elementId", ignore = true)
    @Mapping(target = "partElementId", ignore = true)
    @Mapping(target = "measurementNumber", ignore = true)
    void mapToUpdateUltrasonicResidualThicknessMeasurement(
                                            @MappingTarget UltrasonicResidualThicknessMeasurement measurement
                                                         , UltrasonicResidualThicknessMeasurementDto measurementDto
                                                         , LocalDate measurementDate);

    ResponseUltrasonicResidualThicknessMeasurementDto mapToResponseUltrasonicThicknessMeasurementDto(
                                                                    UltrasonicResidualThicknessMeasurement measurement);
}