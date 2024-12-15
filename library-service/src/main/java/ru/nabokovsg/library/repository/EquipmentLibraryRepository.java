package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.EquipmentLibrary;

public interface EquipmentLibraryRepository extends JpaRepository<EquipmentLibrary, Long> {
}