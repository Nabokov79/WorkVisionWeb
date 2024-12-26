package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;

import java.util.Set;

public interface CompletedRepairMeasurementRepository extends JpaRepository<CompletedRepairMeasurement, Long> {

    Set<CompletedRepairMeasurement> findAllByEquipmentId(Long equipmentId);

    Set<CompletedRepairMeasurement> findAllByEquipmentIdAndElementIdAndRepairLibraryId(Long equipmentId
                                                                              , Long elementId
                                                                              , Long repairLibraryId);

    Set<CompletedRepairMeasurement> findAllByEquipmentIdAndElementIdAndPartElementIdAndRepairLibraryId(Long equipmentId
                                                                                              , Long elementId
                                                                                              , Long partElementId
                                                                                              , Long repairLibraryId);
}