package ru.nabokovsg.measurementqc.repository.qualityControl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.qualityControl.VisualMeasurementControl;

import java.util.Set;

public interface VisualMeasurementControlRepository extends JpaRepository<VisualMeasurementControl, Long> {

    Set<VisualMeasurementControl> findAllByWorkJournalId(Long workJournalId);
}