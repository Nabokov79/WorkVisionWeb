package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.IdentifiedDefectMeasurement;

import java.util.Set;

public interface MeasurementParameterValidateService {

    IdentifiedDefectMeasurement searchIdentifiedDefectMeasurementDuplicate(IdentifiedDefectMeasurement identifiedDefect
                                                                  , Set<IdentifiedDefectMeasurement> identifiedDefects);

    CompletedRepairMeasurement searchCompletedRepairMeasurementDuplicate(CompletedRepairMeasurement completedRepair
                                                                    , Set<CompletedRepairMeasurement> completedRepairs);
}