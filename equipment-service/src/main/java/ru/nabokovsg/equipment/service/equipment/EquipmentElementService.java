package ru.nabokovsg.equipment.service.equipment;

import ru.nabokovsg.equipment.dto.equipmentElement.NewEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.UpdateEquipmentElementDto;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;

import java.util.List;

public interface EquipmentElementService {

    ResponseEquipmentElementDto save(NewEquipmentElementDto elementDto);

    ResponseEquipmentElementDto update(UpdateEquipmentElementDto elementDto);

    EquipmentElement get(Long id);

    List<ResponseEquipmentElementDto> getAll(Long equipmentId);

    void delete(Long id);
}