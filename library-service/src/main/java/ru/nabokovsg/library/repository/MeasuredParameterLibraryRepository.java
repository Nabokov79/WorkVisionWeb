package ru.nabokovsg.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nabokovsg.library.model.MeasurementParameterLibrary;

public interface MeasuredParameterLibraryRepository extends JpaRepository<MeasurementParameterLibrary, Long> {
}