package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.LibraryDataType;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.MeasuredParameter;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.ParameterMeasurementBuilder;

import java.util.List;
import java.util.Set;

public interface MeasuredParameterService {

    Set<MeasuredParameter> save(ParameterMeasurementBuilder builder);

    Set<MeasuredParameter> update(Set<MeasuredParameter> measuredParameters
                                , List<UpdateMeasuredParameterDto> measuredParametersDto);

    void deleteAll(LibraryDataType libraryDataType, Long id);
}