package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;

@Mapper(componentModel = "spring")
public interface IdentifiedDefectMeasurementMapper {

    IdentifiedDefectMeasurement mapToIdentifiedDefectMeasurement(NewIdentifiedDefectMeasurementDto identifiedDefectDto);

    IdentifiedDefectMeasurement mapToUpdateIdentifiedDefectMeasurement(UpdateIdentifiedDefectMeasurementDto identifiedDefectDto);

    @Mapping(source = "typeDefectLibrary.defectName", target = "defectName")
    @Mapping(source = "diagnosedData.elementId", target = "elementId")
    @Mapping(source = "diagnosedData.elementName", target = "elementName")
    @Mapping(source = "diagnosedData.partElementId", target = "partElementId")
    @Mapping(source = "diagnosedData.partElementName", target = "partElementName")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "measuredParameters", ignore = true)
    void mapWitEquipmentDiagnosedData(@MappingTarget IdentifiedDefectMeasurement defect
                                                   , DefectLibrary typeDefectLibrary
                                                   , EquipmentDiagnosedData diagnosedData);

    ResponseIdentifiedDefectMeasurementDto mapToResponseIdentifiedDefectDto(IdentifiedDefectMeasurement identifiedDefect);
}