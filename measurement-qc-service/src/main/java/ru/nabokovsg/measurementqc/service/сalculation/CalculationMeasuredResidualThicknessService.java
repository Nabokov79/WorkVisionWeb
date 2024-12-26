package ru.nabokovsg.measurementqc.service.сalculation;

import ru.nabokovsg.measurementqc.dto.integration.AcceptableResidualThicknessDto;
import ru.nabokovsg.measurementqc.dto.integration.EquipmentDto;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

public interface CalculationMeasuredResidualThicknessService {

    void calculation(UltrasonicResidualThicknessMeasurement measurement
                   , EquipmentDto equipmentData
                   , AcceptableResidualThicknessDto acceptableThickness);

    void updateResidualThicknessMeasurementsEquipmentElements(IdentifiedDefectMeasurement identifiedDefect
                                                            , EquipmentDto equipment
                                                            , AcceptableResidualThicknessDto acceptableThickness);
}