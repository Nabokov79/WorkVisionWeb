package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.repairLibrary.NewRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseShortRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.UpdateRepairLibraryDto;

import java.util.List;

public interface RepairLibraryService {

    ResponseRepairLibraryDto save(NewRepairLibraryDto repairDto);

    ResponseRepairLibraryDto update(UpdateRepairLibraryDto repairDto);

    ResponseRepairLibraryDto get(Long id);

    List<ResponseShortRepairLibraryDto> getAll();

    void delete(Long id);
}