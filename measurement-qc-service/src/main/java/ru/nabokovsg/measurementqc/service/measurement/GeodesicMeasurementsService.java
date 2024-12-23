package ru.nabokovsg.measurementqc.service.measurement;

import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.NewGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.ResponseGeodesicMeasurementsDto;

import java.util.List;

public interface GeodesicMeasurementsService {

    List<ResponseGeodesicMeasurementsDto> save(NewGeodesicMeasurementsDto measurementDto);

    List<ResponseGeodesicMeasurementsDto> getAll(Long equipmentId);

    void delete(Long id);
}