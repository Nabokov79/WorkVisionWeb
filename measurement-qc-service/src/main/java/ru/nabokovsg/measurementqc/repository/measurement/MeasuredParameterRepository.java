package ru.nabokovsg.measurementqc.repository.measurement;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.measurementqc.model.measurement.MeasuredParameter;

public interface MeasuredParameterRepository extends JpaRepository<MeasuredParameter, Long> {
}