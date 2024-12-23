package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

import java.util.Set;

public interface UltrasonicResidualThicknessMeasurementRepository
                                                 extends JpaRepository<UltrasonicResidualThicknessMeasurement, Long> {

    UltrasonicResidualThicknessMeasurement findByEquipmentIdAndElementIdAndMeasurementNumber(
                                                          Long equipmentId, Long elementId, Integer measurementNumber);

    UltrasonicResidualThicknessMeasurement findByEquipmentIdAndElementIdAndPartElementIdAndMeasurementNumber(
                                       Long equipmentId, Long elementId, Long partElementId, Integer measurementNumber);

    Set<UltrasonicResidualThicknessMeasurement> findAllByEquipmentIdOrderByMeasurementNumberDesc(Long equipmentId);

    Set<UltrasonicResidualThicknessMeasurement> findAllByElementId(Long elementId);

    Set<UltrasonicResidualThicknessMeasurement> findAllByElementIdAndPartElementId(Long elementId, Long partElementId);
}