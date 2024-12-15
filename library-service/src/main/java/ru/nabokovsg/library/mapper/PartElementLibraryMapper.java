package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.partElementLibrary.NewPartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.ResponsePartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.UpdatePartElementLibraryDto;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.PartElementLibrary;

@Mapper(componentModel = "spring")
public interface PartElementLibraryMapper {

    PartElementLibrary mapToPartElementLibrary(NewPartElementLibraryDto partElement);

    @Mapping(target = "id", ignore = true)
    PartElementLibrary mapToUpdatePartElementLibrary(@MappingTarget PartElementLibrary partElement
                                                                  , UpdatePartElementLibraryDto partElementDto);


    @Mapping(target = "id", ignore = true)
    void mapWithElementLibrary(@MappingTarget PartElementLibrary partElement, ElementLibrary elementLibrary);

    ResponsePartElementLibraryDto mapToResponsePartElementLibraryDto(PartElementLibrary partElement);

    @Mapping(target = "id", ignore = true)
    PartElementLibrary mapToCopyPartElementLibrary(PartElementLibrary partElement, ElementLibrary elementLibrary);
}