package ru.nabokovsg.equipment.mapper.equipment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.equipment.dto.equipmentElement.NewEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.UpdateEquipmentElementDto;
import ru.nabokovsg.equipment.model.equipment.Equipment;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.StandardSize;
import ru.nabokovsg.equipment.model.library.ElementLibrary;

@Mapper(componentModel = "spring")
public interface EquipmentElementMapper {

    @Mapping(source = "elementLibrary.id", target = "elementLibraryId")
    @Mapping(source = "elementLibrary.elementName", target = "elementName")
    @Mapping(source = "equipment", target = "equipment")
    @Mapping(target = "standardSize", ignore = true)
    @Mapping(target = "partsElement", ignore = true)
    @Mapping(target = "id", ignore = true)
    EquipmentElement mapToElement(ElementLibrary elementLibrary
                                , Equipment equipment);

    void mapWithStandardSize(@MappingTarget EquipmentElement element, String standardSize);

    void mapToUpdateElement(@MappingTarget EquipmentElement element, String standardSize);

    ResponseEquipmentElementDto mapToResponseEquipmentElementDto(EquipmentElement element);

    StandardSize mapToStandardSize(NewEquipmentElementDto elementDto);

    StandardSize mapToUpdateStandardSize(UpdateEquipmentElementDto elementDto);
}