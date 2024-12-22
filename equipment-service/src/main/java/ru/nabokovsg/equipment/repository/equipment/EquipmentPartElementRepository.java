package ru.nabokovsg.equipment.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;

import java.util.Set;

public interface EquipmentPartElementRepository extends JpaRepository<EquipmentPartElement, Long> {

    Set<EquipmentPartElement> findAllByPartElementLibraryId(Long partElementLibraryId);
}