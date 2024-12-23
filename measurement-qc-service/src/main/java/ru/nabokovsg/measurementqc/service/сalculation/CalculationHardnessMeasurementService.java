package ru.nabokovsg.measurementqc.service.сalculation;

import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;

public interface CalculationHardnessMeasurementService {

    int getAverageMeasurementValue(Integer measurementValue, Integer measurementValueDto);

    void setMeasurementStatus(HardnessMeasurement measurement, AcceptableMetalHardness acceptableHardness);
}