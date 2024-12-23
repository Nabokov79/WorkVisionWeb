package ru.nabokovsg.measurementqc.service.сalculation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.mapper.measurement.CalculationHardnessMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.HardnessMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.MeasurementStatus;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class CalculationHardnessMeasurementServiceImpl implements CalculationHardnessMeasurementService {

    private final CalculationHardnessMeasurementMapper mapper;

    @Override
    public int getAverageMeasurementValue(Integer measurementValue, Integer measurementValueDto) {
        int[] measurements = {measurementValue, measurementValueDto};
        return Arrays.stream(measurements).sum() / measurements.length;
    }

    @Override
    public void setMeasurementStatus(HardnessMeasurement measurement, AcceptableMetalHardness acceptableHardness) {
        if (validateAcceptableMetalHardness(acceptableHardness)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("NO_STANDARD").label
                                          , "NO_STANDARD");
            return;
        }
        if (compareMinAcceptable(measurement, acceptableHardness, true)
                                               && compareMaxAcceptable(measurement, acceptableHardness, true)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("ACCEPTABLE").label
                                          , "ACCEPTABLE");
            return;
        }
        if (compareMinAcceptable(measurement, acceptableHardness, false)
                                               || compareMaxAcceptable(measurement, acceptableHardness, false)) {
            mapper.mapWithMeasurementStatus(measurement
                                          , MeasurementStatus.valueOf("INVALID").label
                                          , "INVALID");
        }
    }

   private boolean validateAcceptableMetalHardness(AcceptableMetalHardness acceptableHardness) {
        return acceptableHardness == null;
    }

    private boolean compareMinAcceptable(HardnessMeasurement measurement
                                       , AcceptableMetalHardness acceptableHardness
                                       , boolean flag) {
        if (flag) {
            return measurement.getMeasurementValue() >= acceptableHardness.getMinAcceptableHardness();
        }
        return measurement.getMeasurementValue() < acceptableHardness.getMinAcceptableHardness();
    }

    private boolean compareMaxAcceptable(HardnessMeasurement measurement
                                       , AcceptableMetalHardness acceptableHardness
                                       , boolean flag) {
        if (flag) {
            return measurement.getMeasurementValue() <= acceptableHardness.getMaxAcceptableHardness();
        }
        return measurement.getMeasurementValue() > acceptableHardness.getMaxAcceptableHardness();
    }
}