package ru.nabokovsg.equipment.repository.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.equipment.model.equipment.Equipment;

import java.util.Set;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Set<Equipment> findAllByBuildingId(Long buildingId);

    boolean existsByEquipmentLibraryIdAndBuildingId(Long equipmentLibraryId, Long buildingId);

    boolean existsByEquipmentLibraryIdAndBuildingIdAndRoom(Long equipmentLibraryId, Long buildingId, String room);
}