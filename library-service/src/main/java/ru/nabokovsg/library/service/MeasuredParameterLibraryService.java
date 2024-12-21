package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.measurementParameterLibrary.NewMeasurementParameterLibraryDto;
import ru.nabokovsg.library.dto.measurementParameterLibrary.UpdateMeasurementParameterLibraryDto;
import ru.nabokovsg.library.model.MeasurementParameterLibrary;
import ru.nabokovsg.library.model.TypeMeasuredParameterBuilder;

import java.util.List;
import java.util.Set;

public interface MeasuredParameterLibraryService {

    Set<MeasurementParameterLibrary> save(TypeMeasuredParameterBuilder builder
                                            , List<NewMeasurementParameterLibraryDto> measuredParameters);

    Set<MeasurementParameterLibrary> update(Set<MeasurementParameterLibrary> measuredParametersDb
                                              , List<UpdateMeasurementParameterLibraryDto> measuredParameters);
}