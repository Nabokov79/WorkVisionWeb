package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.AcceptableResidualThickness;

import java.util.Set;

public interface AcceptableResidualThicknessRepository extends JpaRepository<AcceptableResidualThickness, Long> {

    Set<AcceptableResidualThickness> findAllByEquipmentLibraryId(Long equipmentLibraryId);

    boolean existsByEquipmentLibraryIdAndElementLibraryIdAndStandardSize(Long equipmentLibraryId
                                                                     , Long elementLibraryId
                                                                     , String standardSizeString);

    boolean existsByEquipmentLibraryIdAndElementLibraryIdAndPartElementLibraryIdAndStandardSize(
                                                                                           Long equipmentLibraryId
                                                                                         , Long elementLibraryId
                                                                                         , Long partElementLibraryId
                                                                                         , String standardSizeString);
}