package ru.nabokovsg.equipment.service.library;

import ru.nabokovsg.equipment.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.EquipmentLibrary;

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