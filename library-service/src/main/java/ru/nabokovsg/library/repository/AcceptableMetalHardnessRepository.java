package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.AcceptableMetalHardness;

import java.util.Set;

public interface AcceptableMetalHardnessRepository extends JpaRepository<AcceptableMetalHardness, Long> {

    Set<AcceptableMetalHardness> findAllByEquipmentLibraryId(Long equipmentLibraryId);

    boolean existsByEquipmentLibraryIdAndElementLibraryIdAndStandardSize(Long equipmentLibraryId
                                                                       , Long elementLibraryId
                                                                       , String standardSizeString);

    boolean existsByEquipmentLibraryIdAndElementLibraryIdAndPartElementLibraryIdAndStandardSize(
                                                                                             Long equipmentLibraryId
                                                                                           , Long elementLibraryId
                                                                                           , Long partElementLibraryId
                                                                                           , String standardSizeString);
}