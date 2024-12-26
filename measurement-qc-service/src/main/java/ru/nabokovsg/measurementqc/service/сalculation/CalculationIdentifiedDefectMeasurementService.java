package ru.nabokovsg.measurementqc.service.сalculation;

import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

import java.util.Map;

public interface CalculationIdentifiedDefectMeasurementService {

    void calculateByResidualThickness(IdentifiedDefectMeasurement identifiedDefect, AcceptableResidualThicknessDto acceptableThickness);

    void updateUnacceptableByResidualThickness(UltrasonicResidualThicknessMeasurement measurement
                                                    , AcceptableResidualThicknessDto acceptableThickness);

    void calculateByMaxAllowedValue(LibraryDto defectLibrary
                                  , IdentifiedDefectMeasurement identifiedDefect
                                  , Map<Long, Double> measurementValues);

}