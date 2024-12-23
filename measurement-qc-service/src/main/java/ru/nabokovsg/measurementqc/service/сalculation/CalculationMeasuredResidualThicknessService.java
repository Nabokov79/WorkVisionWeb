package ru.nabokovsg.measurementqc.service.сalculation;

import ru.nabokovsg.measurementqc.dto.equipment.EquipmentDto;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.UltrasonicResidualThicknessMeasurement;

public interface CalculationMeasuredResidualThicknessService {

    void calculation(UltrasonicResidualThicknessMeasurement measurement
                   , EquipmentDto equipmentData
                   , AcceptableResidualThickness acceptableThickness);

    void updateResidualThicknessMeasurementsEquipmentElements(IdentifiedDefectMeasurement identifiedDefect);
}