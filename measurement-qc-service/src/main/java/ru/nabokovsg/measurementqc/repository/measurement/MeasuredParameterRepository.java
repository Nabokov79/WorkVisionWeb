package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.MeasuredParameter;

public interface MeasuredParameterRepository extends JpaRepository<MeasuredParameter, Long> {


    @Modifying
    @Transactional
    void deleteAllByIdentifiedDefectId(Long id);

    @Modifying
    @Transactional
    void deleteAllByCompletedRepairId(Long id);

    @Modifying
    @Transactional
    void deleteAllByVisualMeasurementControlId(Long id);
}