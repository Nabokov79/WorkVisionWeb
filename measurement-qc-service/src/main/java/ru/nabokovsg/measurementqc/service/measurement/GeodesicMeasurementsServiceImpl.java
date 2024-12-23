package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.NewGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.ResponseGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.measurement.GeodesicMeasurementsMapper;
import ru.nabokovsg.measurementqc.model.measurement.GeodesicMeasurements;
import ru.nabokovsg.measurementqc.repository.measurement.GeodesicMeasurementsRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeodesicMeasurementsServiceImpl implements GeodesicMeasurementsService {

    private final GeodesicMeasurementsRepository repository;
    private final GeodesicMeasurementsMapper mapper;

    @Override
    public List<ResponseGeodesicMeasurementsDto> save(NewGeodesicMeasurementsDto measurementDto) {
        Map<Integer, GeodesicMeasurements> measurements = repository.findAllByEquipmentId(
                        measurementDto.getEquipmentId())
                .stream()
                .collect(Collectors.toMap(GeodesicMeasurements::getNumberMeasurementLocation, g -> g));
        GeodesicMeasurements measurement = measurements.get(measurementDto.getNumberMeasurementLocation());
        if (measurement == null) {
            measurements.put(measurementDto.getNumberMeasurementLocation()
                    , repository.save(mapper.mapToGeodesicMeasurementsPoint(measurementDto, 1)));
        } else {
            measurements.put(measurement.getNumberMeasurementLocation(), update(measurementDto, measurement));
        }
        return measurements.values()
                .stream()
                .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                .toList();
    }

    private GeodesicMeasurements update(NewGeodesicMeasurementsDto measurementDto
                                      , GeodesicMeasurements measurement) {
        return repository.save(mapper.mapToUpdateGeodesicMeasurementsPoint(measurement
                                                                         , measurementDto
                                                                         , measurement.getMeasurementNumber()));
    }

    @Override
    public List<ResponseGeodesicMeasurementsDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseGeodesicMeasurementsPointDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("GeodesicMeasurement with id=%s not found for delete", id));
    }
}