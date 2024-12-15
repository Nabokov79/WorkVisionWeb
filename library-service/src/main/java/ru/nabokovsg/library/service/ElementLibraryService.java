package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.EquipmentLibrary;

import java.util.List;
import java.util.Set;

public interface ElementLibraryService {

    ResponseElementLibraryDto save(NewElementLibraryDto elementDto);

    ResponseElementLibraryDto update(UpdateElementLibraryDto elementDto);

    void copy(EquipmentLibrary equipmentLibrary, Set<ElementLibrary> elements);

    List<ResponseElementLibraryDto> getAll(Long equipmentLibraryId);

    void delete(Long id);

    ElementLibrary getById(long id);
}