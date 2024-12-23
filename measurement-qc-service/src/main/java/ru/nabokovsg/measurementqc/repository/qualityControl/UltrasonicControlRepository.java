package ru.nabokovsg.measurementqc.repository.qualityControl;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.qualityControl.UltrasonicControl;

import java.util.Set;

public interface UltrasonicControlRepository extends JpaRepository<UltrasonicControl, Long> {

    Set<UltrasonicControl> findAllByWorkJournalId(Long workJournalId);
}