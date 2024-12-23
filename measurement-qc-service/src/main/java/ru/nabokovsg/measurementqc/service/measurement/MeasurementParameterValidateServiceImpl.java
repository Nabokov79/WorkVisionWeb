package ru.nabokovsg.measurementqc.service.measurement;

import org.springframework.stereotype.Component;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.MeasurementParameterType;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.MeasuredParameter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MeasurementParameterValidateServiceImpl implements MeasurementParameterValidateService {

    @Override
    public IdentifiedDefectMeasurement searchIdentifiedDefectMeasurementDuplicate(
                                                           IdentifiedDefectMeasurement identifiedDefect
                                                         , Set<IdentifiedDefectMeasurement> identifiedDefects) {
        if (!identifiedDefects.isEmpty()) {
            Map<Long, MeasuredParameter> parameters = identifiedDefect.getMeasuredParameters()
                                                .stream()
                                                .collect(Collectors.toMap(MeasuredParameter::getParameterId, p -> p));
            for (IdentifiedDefectMeasurement defect : identifiedDefects) {
                if (compareMeasuredParameter(defect.getMeasuredParameters(), parameters)) {
                    return defect;
                }
            }
        }
        return identifiedDefect;
    }

    @Override
    public CompletedRepairMeasurement searchCompletedRepairMeasurementDuplicate(
                                                                    CompletedRepairMeasurement completedRepair
                                                                  , Set<CompletedRepairMeasurement> completedRepairs) {
        if (!completedRepairs.isEmpty()) {
            Map<Long, MeasuredParameter> parameters = completedRepair.getMeasuredParameters()
                                                 .stream()
                                                 .collect(Collectors.toMap(MeasuredParameter::getParameterId, p -> p));
            for (CompletedRepairMeasurement repair : completedRepairs) {
                if (compareMeasuredParameter(repair.getMeasuredParameters(), parameters)) {
                    return repair;
                }
            }
        }
        return completedRepair;
    }

    public boolean compareMeasuredParameter(Set<MeasuredParameter> measuredParameters
                                                         , Map<Long, MeasuredParameter> parameters) {
        int coincidences = 0;
        String quantityName = MeasurementParameterType.valueOf("QUANTITY").label;
        Map<MeasuredParameter, MeasuredParameter> quantity = new HashMap<>(1);
        for (MeasuredParameter parameterDb : measuredParameters) {
            MeasuredParameter parameter = parameters.get(parameterDb.getParameterId());
            if (parameterDb.getParameterName().equals(quantityName)) {
                quantity.put(parameterDb, parameter);
                coincidences++;
            } else if (parameterDb.getValue().equals(parameter.getValue())) {
                coincidences++;
            }
        }
        if (coincidences == parameters.size()) {
            quantity.forEach((k, v) -> {
                measuredParameters.remove(k);
                k.setValue(k.getValue() + v.getValue());
                measuredParameters.add(k);
            });
            return true;
        }
        return false;
    }
}