package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;

import java.util.List;

public interface IdentifiedDefectMeasurementService {

    ResponseIdentifiedDefectMeasurementDto save(NewIdentifiedDefectMeasurementDto defectDto);

    ResponseIdentifiedDefectMeasurementDto update(UpdateIdentifiedDefectMeasurementDto defectDto);

    ResponseIdentifiedDefectMeasurementDto get(Long id);

    List<ResponseIdentifiedDefectMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);
}