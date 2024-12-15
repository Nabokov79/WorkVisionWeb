package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.AcceptableDeviationsGeodesy;

import java.util.Set;

public interface AcceptableDeviationsGeodesyRepository extends JpaRepository<AcceptableDeviationsGeodesy, Long> {

    AcceptableDeviationsGeodesy findByEquipmentLibraryIdAndFullAndOld(Long equipmentLibraryId, Boolean full, Boolean old);

    Set<AcceptableDeviationsGeodesy> findAllByEquipmentLibraryId(Long equipmentLibraryId);
}