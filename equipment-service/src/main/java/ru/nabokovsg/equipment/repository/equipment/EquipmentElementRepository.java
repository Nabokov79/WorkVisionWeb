package ru.nabokovsg.equipment.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;

import java.util.Set;

public interface EquipmentElementRepository extends JpaRepository<EquipmentElement, Long> {

    Set<EquipmentElement> findByEquipmentId(Long equipmentId);

    EquipmentElement findByEquipmentIdAndElementLibraryIdAndStandardSize(Long equipmentId
                                                                       , Long elementLibraryId
                                                                       , String standardSize);

    EquipmentElement findByEquipmentIdAndElementLibraryId(Long equipmentId, Long elementLibraryId);

    Set<EquipmentElement> findAllByElementLibraryId(Long elementLibraryId);
}