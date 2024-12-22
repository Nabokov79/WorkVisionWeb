package ru.nabokovsg.equipment.mapper.library;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.equipment.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.EquipmentLibrary;

@Mapper(componentModel = "spring")
public interface ElementLibraryMapper {

    @Mapping(target = "id", ignore = true)
    ElementLibrary mapToElementLibrary(NewElementLibraryDto element, EquipmentLibrary equipmentLibrary);

    @Mapping(source = "element.id", target = "id")
    ElementLibrary mapToUpdateElementLibrary(UpdateElementLibraryDto element, EquipmentLibrary equipmentLibrary);

    ResponseElementLibraryDto mapToResponseElementLibraryDto(ElementLibrary element);


}