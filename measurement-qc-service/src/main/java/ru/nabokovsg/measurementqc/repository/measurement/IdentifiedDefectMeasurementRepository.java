package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;

import java.util.Set;

public interface IdentifiedDefectMeasurementRepository extends JpaRepository<IdentifiedDefectMeasurement, Long> {

    Set<IdentifiedDefectMeasurement> findAllByEquipmentId(Long equipmentId);

    Set<IdentifiedDefectMeasurement> findAllByEquipmentIdAndElementIdAndDefectId(Long equipmentId
                                                                               , Long elementId
                                                                               , Long defectId);

    Set<IdentifiedDefectMeasurement> findAllByEquipmentIdAndElementIdAndPartElementIdAndDefectId(Long equipmentId
                                                                                               , Long elementId
                                                                                               , Long partElementId
                                                                                               , Long defectId);
}