package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.RepairLibrary;

import java.util.Optional;

public interface RepairLibraryRepository extends JpaRepository<RepairLibrary, Long> {

    boolean existsByRepairName(String repairName);
}