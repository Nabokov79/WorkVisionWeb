package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.DefectLibrary;

public interface DefectLibraryRepository extends JpaRepository<DefectLibrary, Long> {

    DefectLibrary findByDefectName(String defectName);
}