package ru.nabokovsg.equipment.service.library;

import ru.nabokovsg.equipment.dto.partElementLibrary.NewPartElementLibraryDto;
import ru.nabokovsg.equipment.dto.partElementLibrary.ResponsePartElementLibraryDto;
import ru.nabokovsg.equipment.dto.partElementLibrary.UpdatePartElementLibraryDto;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;

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