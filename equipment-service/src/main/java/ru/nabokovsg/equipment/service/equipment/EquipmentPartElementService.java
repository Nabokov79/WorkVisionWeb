package ru.nabokovsg.equipment.service.equipment;

import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;

import java.util.Set;

public interface EquipmentPartElementService {

    void save(EquipmentElement element, Long partElementLibraryId, String standardSize);

    void update(Set<EquipmentPartElement> partsElement, Long partElementId, String standardSize);
}