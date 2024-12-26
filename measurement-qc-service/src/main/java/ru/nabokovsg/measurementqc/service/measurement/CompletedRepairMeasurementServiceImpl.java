package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.client.MeasurementQCClient;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.integration.LibraryDto;
import ru.nabokovsg.measurementqc.exceptions.NotFoundException;
import ru.nabokovsg.measurementqc.mapper.measurement.CompletedRepairMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;
import ru.nabokovsg.measurementqc.model.measurement.LibraryDataType;
import ru.nabokovsg.measurementqc.model.measurement.ParameterMeasurementBuilder;
import ru.nabokovsg.measurementqc.repository.measurement.CompletedRepairMeasurementRepository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompletedRepairMeasurementServiceImpl implements CompletedRepairMeasurementService {

    private final CompletedRepairMeasurementRepository repository;
    private final CompletedRepairMeasurementMapper mapper;
    private final MeasuredParameterService measuredParameterService;
    private final MeasurementParameterValidateService validateService;
    private final MeasurementQCClient client;

    @Override
    public ResponseCompletedRepairMeasurementDto save(NewCompletedRepairMeasurementDto repairDto) {
        CompletedRepairMeasurement repair = mapper.mapToCompletedRepair(repairDto);
        repair = validateService.searchCompletedRepairMeasurementDuplicate(repair, getAllByPredicate(repair));
        if (repair.getId() == null) {
            LibraryDto repairLibrary = client.getLibraryData(repairDto.getRepairLibraryId(), LibraryDataType.REPAIR);
            mapper.mapWitEquipmentData(repair
                                     , repairLibrary
                                     , client.getEquipmentData(repairDto.getElementId(), repairDto.getPartElementId()));
            repair = repository.save(repair);
            repair.setMeasuredParameters(measuredParameterService.save(
                    new ParameterMeasurementBuilder.Builder()
                                                   .libraryDataType(LibraryDataType.REPAIR)
                                                   .completedRepair(repair)
                                                   .measurementParameterLibraries(repairLibrary.getMeasuredParameters())
                                                   .newMeasuredParameters(repairDto.getMeasuredParameters())
                                                   .build()));
        } else {
            repair.setMeasuredParameters(measuredParameterService.update(repair.getMeasuredParameters(), null));
        }
        return mapper.mapToResponseCompletedRepairDto(repair);
    }

    @Override
    public ResponseCompletedRepairMeasurementDto update(UpdateCompletedRepairMeasurementDto repairDto) {
        CompletedRepairMeasurement repair = getById(repairDto.getId());
        CompletedRepairMeasurement duplicate = validateService.searchCompletedRepairMeasurementDuplicate(
                                                                            mapper.mapToUpdateCompletedRepair(repairDto)
                                                                          , getAllByPredicate(repair));
        if (Objects.equals(repair.getId(), duplicate.getId())) {
            repair.setMeasuredParameters(measuredParameterService.update(repair.getMeasuredParameters(), null));
            return mapper.mapToResponseCompletedRepairDto(repair);
        } else {
            duplicate.setMeasuredParameters(measuredParameterService.update(duplicate.getMeasuredParameters(), null));
            delete(repairDto.getId());
            return mapper.mapToResponseCompletedRepairDto(duplicate);
        }
    }

    @Override
    public ResponseCompletedRepairMeasurementDto get(Long id) {
        return mapper.mapToResponseCompletedRepairDto(getById(id));
    }

    @Override
    public List<ResponseCompletedRepairMeasurementDto> getAll(Long equipmentId) {
        return repository.findAllByEquipmentId(equipmentId)
                                      .stream()
                                      .map(mapper::mapToResponseCompletedRepairDto)
                                      .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("CompletedRepair with id=%s not found for delete", id));
    }

    private CompletedRepairMeasurement getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("CompletedRepair with id=%s not found", id)));
    }

    private Set<CompletedRepairMeasurement> getAllByPredicate(CompletedRepairMeasurement repair) {
        if (repair.getPartElementId() != null) {
            return repository.findAllByEquipmentIdAndElementIdAndPartElementIdAndRepairLibraryId(repair.getEquipmentId()
                                                                                        , repair.getElementId()
                                                                                        , repair.getPartElementId()
                                                                                        , repair.getRepairLibraryId());
        } else {
            return repository.findAllByEquipmentIdAndElementIdAndRepairLibraryId(repair.getEquipmentId()
                                                                               , repair.getElementId()
                                                                               , repair.getRepairLibraryId());
        }
    }
}