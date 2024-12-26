package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;

import java.util.Set;

public interface HardnessMeasurementRepository extends JpaRepository<HardnessMeasurement, Long> {

    Set<HardnessMeasurement> findAllByEquipmentId(Long equipmentId);

    HardnessMeasurement findByEquipmentIdAndElementIdAndMeasurementNumber(Long equipmentId
                                                                        , Long elementId
                                                                        , Integer measurementNumber);

    HardnessMeasurement findByEquipmentIdAndElementIdAndPartElementIdAndMeasurementNumber(Long equipmentId
                                                                                        , Long elementId
                                                                                        , Long partElementId
                                                                                        , Integer measurementNumber);
}