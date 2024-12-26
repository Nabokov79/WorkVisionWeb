package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;

@Mapper(componentModel = "spring")
public interface IdentifiedDefectMeasurementMapper {

    IdentifiedDefectMeasurement mapToIdentifiedDefectMeasurement(NewIdentifiedDefectMeasurementDto identifiedDefectDto);

    IdentifiedDefectMeasurement mapToUpdateIdentifiedDefectMeasurement(UpdateIdentifiedDefectMeasurementDto identifiedDefectDto);

    @Mapping(source = "repairLibrary.defectName", target = "defectName")
    @Mapping(source = "equipment.elementId", target = "elementId")
    @Mapping(source = "equipment.elementName", target = "elementName")
    @Mapping(source = "equipment.partElementId", target = "partElementId")
    @Mapping(source = "equipment.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measuredParameters", ignore = true)
    void mapWitEquipmentDiagnosedData(@MappingTarget IdentifiedDefectMeasurement defect
                                                   , LibraryDto repairLibrary
                                                   , EquipmentDto equipment);

    ResponseIdentifiedDefectMeasurementDto mapToResponseIdentifiedDefectDto(IdentifiedDefectMeasurement identifiedDefect);
}