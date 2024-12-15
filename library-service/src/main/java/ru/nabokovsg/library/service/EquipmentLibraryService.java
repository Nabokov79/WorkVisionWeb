package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.equipmentLibrary.NewEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.ResponseEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.UpdateEquipmentLibraryDto;
import ru.nabokovsg.library.model.EquipmentLibrary;

import java.util.List;

public interface EquipmentLibraryService {

    ResponseEquipmentLibraryDto save(NewEquipmentLibraryDto equipmentDto);

    ResponseEquipmentLibraryDto update(UpdateEquipmentLibraryDto equipmentDto);

   ResponseEquipmentLibraryDto get(Long id);

    List<ResponseEquipmentLibraryDto> getAll();

    void delete(Long id);

    EquipmentLibrary getById(Long id);
}