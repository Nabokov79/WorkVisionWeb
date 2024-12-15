package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.library.dto.equipmentLibrary.NewEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.ResponseEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.UpdateEquipmentLibraryDto;
import ru.nabokovsg.library.model.EquipmentLibrary;

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