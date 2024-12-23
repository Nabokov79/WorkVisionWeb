package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.ResponseUltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicResidualThicknessMeasurement.UltrasonicResidualThicknessMeasurementDto;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

import java.util.List;

public interface UltrasonicResidualThicknessMeasurementService {

    ResponseUltrasonicResidualThicknessMeasurementDto save(UltrasonicResidualThicknessMeasurementDto measurementDto);

    ResponseUltrasonicResidualThicknessMeasurementDto get(Long id);

    List<ResponseUltrasonicResidualThicknessMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);

    UltrasonicResidualThicknessMeasurement getByPredicateData(UltrasonicResidualThicknessMeasurementDto measurementDto);
}