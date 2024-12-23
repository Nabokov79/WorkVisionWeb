package ru.nabokovsg.measurementqc.service.qualityControl;

import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.UpdateVisualMeasurementControlDto;

import java.util.List;

public interface VisualMeasurementControlService {

    ResponseVisualMeasurementControlDto save(NewVisualMeasurementControlDto defectDto);

    ResponseVisualMeasurementControlDto update(UpdateVisualMeasurementControlDto defectDto);

    ResponseVisualMeasurementControlDto get(Long id);

    List<ResponseVisualMeasurementControlDto> getAll(Long workJournalId);

    void delete(Long id);
}