package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.model.measurement.MeasuredParameter;
import ru.nabokovsg.measurementqc.model.measurement.ParameterMeasurementBuilder;

import java.util.List;
import java.util.Set;

public interface MeasuredParameterService {

    Set<MeasuredParameter> save(ParameterMeasurementBuilder builder);

    Set<MeasuredParameter> update(Set<MeasuredParameter> measuredParameters
                                , List<UpdateMeasuredParameterDto> measuredParametersDto);
}