package ru.nabokovsg.measurementqc.service.сalculation;

import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

import java.util.Map;

public interface CalculationIdentifiedDefectMeasurementService {

    void calculateByResidualThickness(IdentifiedDefectMeasurement identifiedDefect);

    void updateUnacceptableByResidualThickness(UltrasonicResidualThicknessMeasurement measurement
                                                    , AcceptableResidualThickness acceptableThickness);

    void calculateByMaxAllowedValue(DefectLibrary typeDefectLibrary
                                  , IdentifiedDefectMeasurement identifiedDefect
                                  , Map<Long, Double> measurementValues);

}