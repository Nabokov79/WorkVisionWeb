package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.partElementLibrary.NewPartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.ResponsePartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.UpdatePartElementLibraryDto;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.PartElementLibrary;

import java.util.List;
import java.util.Set;

public interface PartElementLibraryService {

    ResponsePartElementLibraryDto save(NewPartElementLibraryDto partElementDto);

    ResponsePartElementLibraryDto update(UpdatePartElementLibraryDto partElementDto);

    void copy(ElementLibrary element, Set<PartElementLibrary> partsElement);

    List<ResponsePartElementLibraryDto> getAll(Long elementLibraryId);

    void delete(Long id);

    PartElementLibrary getById(Long id);
}