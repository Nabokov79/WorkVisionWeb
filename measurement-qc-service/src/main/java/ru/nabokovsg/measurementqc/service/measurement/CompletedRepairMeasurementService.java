package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;

import java.util.List;

public interface CompletedRepairMeasurementService {

    ResponseCompletedRepairMeasurementDto save(NewCompletedRepairMeasurementDto repairDto);

    ResponseCompletedRepairMeasurementDto update(UpdateCompletedRepairMeasurementDto repairDto);

    ResponseCompletedRepairMeasurementDto get(Long id);

    List<ResponseCompletedRepairMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);
}