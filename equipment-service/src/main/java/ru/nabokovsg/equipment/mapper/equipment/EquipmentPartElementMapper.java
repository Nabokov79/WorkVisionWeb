package ru.nabokovsg.equipment.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;

@Mapper(componentModel = "spring")
public interface EquipmentPartElementMapper {

    @Mapping(source = "partElement.id", target = "partElementLibraryId")
    @Mapping(source = "partElement.partElementName", target = "partElementName")
    @Mapping(source = "standardSize", target = "standardSize")
    @Mapping(source = "element", target = "element")
    @Mapping(target = "id", ignore = true)
    EquipmentPartElement mapToEquipmentPartElement(EquipmentElement element
                                                 , PartElementLibrary partElement
                                                 , String standardSize);

    void mapToUpdateEquipmentPartElement(@MappingTarget EquipmentPartElement partElement, String standardSize);
}