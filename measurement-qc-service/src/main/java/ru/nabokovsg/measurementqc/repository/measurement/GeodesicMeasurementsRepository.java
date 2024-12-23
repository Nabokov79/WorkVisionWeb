package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.GeodesicMeasurements;

import java.util.Set;

public interface GeodesicMeasurementsRepository extends JpaRepository<GeodesicMeasurements, Long> {

    Set<GeodesicMeasurements> findAllByEquipmentId(Long equipmentId);
}