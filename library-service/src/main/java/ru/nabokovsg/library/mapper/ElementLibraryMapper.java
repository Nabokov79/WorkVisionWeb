package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.library.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.EquipmentLibrary;

@Mapper(componentModel = "spring")
public interface ElementLibraryMapper {

    @Mapping(target = "id", ignore = true)
    ElementLibrary mapToElementLibrary(NewElementLibraryDto element, EquipmentLibrary equipmentLibrary);

    @Mapping(source = "element.id", target = "id")
    ElementLibrary mapToUpdateElementLibrary(UpdateElementLibraryDto element, EquipmentLibrary equipmentLibrary);

    ResponseElementLibraryDto mapToResponseElementLibraryDto(ElementLibrary element);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "partsElement", ignore = true)
    ElementLibrary mapToCopyElementLibrary(ElementLibrary element, EquipmentLibrary equipmentLibrary);
}