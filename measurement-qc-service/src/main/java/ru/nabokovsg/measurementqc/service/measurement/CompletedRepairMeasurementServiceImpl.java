package ru.nabokovsg.measurementqc.service.measurement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.mapper.measurement.CompletedRepairMeasurementMapper;
import ru.nabokovsg.measurementqc.model.measurement.CompletedRepairMeasurement;
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
    private final RepairLibraryService repairLibraryService;
    private final MeasuredParameterService measuredParameterService;
    private final EquipmentElementService equipmentElementService;
    private final MeasurementParameterValidateService validateService;

    @Override
    public ResponseCompletedRepairMeasurementDto save(NewCompletedRepairMeasurementDto repairDto) {
        CompletedRepairMeasurement repair = mapper.mapToCompletedRepair(repairDto);
        RepairLibrary typeRepairLibrary = repairLibraryService.getById(repairDto.getRepairId());
        repair = validateService.searchCompletedRepairMeasurementDuplicate(repair, getAllByPredicate(repair));
        if (repair.getId() == null) {
            mapper.mapWitEquipmentDiagnosedData(repair
                                        , typeRepairLibrary
                                        , equipmentElementService.getEquipmentDiagnosedData(repairDto.getElementId()));
            repair = repository.save(repair);
            repair.setMeasuredParameters(measuredParameterService.save(
                    new ParameterMeasurementBuilder.Builder()
                                                   .libraryDataType(LibraryDataType.REPAIR)
                                                   .completedRepair(repair)
                                                   .measurementParameterLibraries(
                                                           typeRepairLibrary.getMeasuredParameters())
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
        measuredParameterService.deleteAll(LibraryDataType.REPAIR, id);
        repository.deleteById(id);
    }

    private CompletedRepairMeasurement getById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("CompletedRepair defect with id=%s not found", id)));
    }

    private Set<CompletedRepairMeasurement> getAllByPredicate(CompletedRepairMeasurement repair) {
        if (repair.getPartElementId() != null) {
            return repository.findAllByEquipmentIdAndElementIdAndPartElementIdAndRepairId(repair.getEquipmentId()
                                                                                        , repair.getElementId()
                                                                                        , repair.getPartElementId()
                                                                                        , repair.getRepairId());
        } else {
            return repository.findAllByEquipmentIdAndElementIdAndRepairId(repair.getEquipmentId()
                                                                        , repair.getElementId()
                                                                        , repair.getRepairId());
        }
    }
}