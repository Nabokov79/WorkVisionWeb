package ru.nabokovsg.equipment.mapper.library;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.equipment.dto.equipmentLibrary.NewEquipmentLibraryDto;
import ru.nabokovsg.equipment.dto.equipmentLibrary.ResponseEquipmentLibraryDto;
import ru.nabokovsg.equipment.dto.equipmentLibrary.UpdateEquipmentLibraryDto;
import ru.nabokovsg.equipment.model.library.EquipmentLibrary;

@Mapper(componentModel = "spring")
public interface EquipmentLibraryMapper {

    EquipmentLibrary mapToEquipmentLibrary(NewEquipmentLibraryDto equipment);

    EquipmentLibrary mapToUpdateEquipmentLibrary(UpdateEquipmentLibraryDto equipment);

    ResponseEquipmentLibraryDto mapResponseEquipmentLibraryDto(EquipmentLibrary equipment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "elements", ignore = true)
    @Mapping(target = "volume", ignore = true)
    @Mapping(target = "orientation", ignore = true)
    @Mapping(target = "model", ignore = true)
    EquipmentLibrary mapToCopyEquipmentLibrary(EquipmentLibrary equipment);
}