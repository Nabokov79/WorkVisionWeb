package ru.nabokovsg.equipment.mapper.library;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;

@Mapper(componentModel = "spring")
public interface UpdateNameMapper {

    EquipmentElement updateElementName(@MappingTarget EquipmentElement element, String elementName);

    EquipmentPartElement updatePartElementName(@MappingTarget EquipmentPartElement partElement, String partElementName);
}