package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;

@Mapper(componentModel = "spring")
public interface CompletedRepairMeasurementMapper {

    CompletedRepairMeasurement mapToCompletedRepair(NewCompletedRepairMeasurementDto repairDto);

    CompletedRepairMeasurement mapToUpdateCompletedRepair(UpdateCompletedRepairMeasurementDto repairDto);

    @Mapping(source = "repairLibrary.repairName", target = "repairName")
    @Mapping(source = "equipmentDto.elementName", target = "elementName")
    @Mapping(source = "equipmentDto.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measuredParameters", ignore = true)
    void mapWitEquipmentData(@MappingTarget CompletedRepairMeasurement repair
                                          , LibraryDto repairLibrary
                                          , EquipmentDto equipmentDto);

    ResponseCompletedRepairMeasurementDto mapToResponseCompletedRepairDto(CompletedRepairMeasurement repair);
}