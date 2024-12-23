package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;

@Mapper(componentModel = "spring")
public interface CompletedRepairMeasurementMapper {

    CompletedRepairMeasurement mapToCompletedRepair(NewCompletedRepairMeasurementDto repairDto);

    CompletedRepairMeasurement mapToUpdateCompletedRepair(UpdateCompletedRepairMeasurementDto repairDto);

    @Mapping(source = "typeRepairLibrary.repairName", target = "repairName")
    @Mapping(source = "diagnosedData.elementId", target = "elementId")
    @Mapping(source = "diagnosedData.elementName", target = "elementName")
    @Mapping(source = "diagnosedData.partElementId", target = "partElementId")
    @Mapping(source = "diagnosedData.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measuredParameters", ignore = true)
    void mapWitEquipmentDiagnosedData(@MappingTarget CompletedRepairMeasurement repair
                                                   , RepairLibrary typeRepairLibrary
                                                   , EquipmentDiagnosedData diagnosedData);

    ResponseCompletedRepairMeasurementDto mapToResponseCompletedRepairDto(CompletedRepairMeasurement repair);
}