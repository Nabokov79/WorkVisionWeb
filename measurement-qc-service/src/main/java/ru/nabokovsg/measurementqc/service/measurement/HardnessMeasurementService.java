package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.HardnessMeasurementDto;
import ru.nabokovsg.measurementqc.dto.hardnessMeasurement.ResponseElementHardnessMeasurementDto;

import java.util.List;

public interface HardnessMeasurementService {

    ResponseElementHardnessMeasurementDto save(HardnessMeasurementDto measurementDto);

    ResponseElementHardnessMeasurementDto get(Long id);

    List<ResponseElementHardnessMeasurementDto> getAll(Long equipmentId);

    void delete(Long id);
}