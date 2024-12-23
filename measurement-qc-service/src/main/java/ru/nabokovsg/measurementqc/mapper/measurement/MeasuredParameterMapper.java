package ru.nabokovsg.measurementqc.mapper.measurement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.equipmentDiagnosedQCL.model.library.MeasurementParameterLibrary;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.IdentifiedDefectMeasurement;
import ru.nabokovsg.equipmentDiagnosedQCL.model.measurement.MeasuredParameter;
import ru.nabokovsg.equipmentDiagnosedQCL.model.qualityControl.VisualMeasurementControl;

@Mapper(componentModel = "spring")
public interface MeasuredParameterMapper {

    @Mapping(source = "parameterLibrary.id", target = "parameterId")
    @Mapping(source = "parameterLibrary.parameterName", target = "parameterName")
    @Mapping(source = "parameterLibrary.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "value", target = "value")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "completedRepair", ignore = true)
    MeasuredParameter mapWithIdentifiedDefect(MeasurementParameterLibrary parameterLibrary
                                            , Double value
                                            , IdentifiedDefectMeasurement identifiedDefect);

    @Mapping(source = "parameterLibrary.id", target = "parameterId")
    @Mapping(source = "parameterLibrary.parameterName", target = "parameterName")
    @Mapping(source = "parameterLibrary.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "value", target = "value")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identifiedDefect", ignore = true)
    @Mapping(target = "completedRepair", ignore = true)
    MeasuredParameter mapWithDefect(MeasurementParameterLibrary parameterLibrary
                                  , Double value
                                  , VisualMeasurementControl defect);

    @Mapping(source = "parameterLibrary.id", target = "parameterId")
    @Mapping(source = "parameterLibrary.parameterName", target = "parameterName")
    @Mapping(source = "parameterLibrary.unitMeasurement", target = "unitMeasurement")
    @Mapping(source = "value", target = "value")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identifiedDefect", ignore = true)
    @Mapping(target = "visualMeasurementControl", ignore = true)
    MeasuredParameter mapWithCompletedRepair(MeasurementParameterLibrary parameterLibrary
                                           , Double value
                                           , CompletedRepairMeasurement repair);

    @Mapping(source = "value", target = "value")
    void mapToUpdateMeasuredParameter(@MappingTarget MeasuredParameter parameter, Double value);
}