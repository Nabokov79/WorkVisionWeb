package ru.nabokovsg.equipment.service.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.dto.equipment.NewEquipmentDto;
import ru.nabokovsg.equipment.dto.equipment.ResponseEquipmentDto;
import ru.nabokovsg.equipment.dto.equipment.ResponseShortEquipmentDto;
import ru.nabokovsg.equipment.dto.equipment.UpdateEquipmentDto;
import ru.nabokovsg.equipment.exceptions.BadRequestException;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.equipment.EquipmentMapper;
import ru.nabokovsg.equipment.model.equipment.Equipment;
import ru.nabokovsg.equipment.repository.equipment.EquipmentRepository;
import ru.nabokovsg.equipment.service.library.EquipmentLibraryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository repository;
    private final EquipmentMapper mapper;
    private final EquipmentLibraryService equipmentLibraryService;

    @Override
    public ResponseEquipmentDto save(NewEquipmentDto equipmentDto) {
        if (getByPredicate(equipmentDto)) {
            throw new BadRequestException(String.format("Equipment found: %s", equipmentDto));
        }
        return mapper.mapToResponseEquipmentDto(
                repository.save(mapper.mapToEquipment(equipmentDto
                        , equipmentLibraryService.getById(equipmentDto.getEquipmentLibraryId())))
        );
    }

    @Override
    public ResponseEquipmentDto update(UpdateEquipmentDto equipmentDto) {
        if (repository.existsById(equipmentDto.getId())) {
            return mapper.mapToResponseEquipmentDto(
                    repository.save(mapper.mapToUpdateEquipment(equipmentDto))
            );
        }
        throw new NotFoundException(String.format("Equipment not found for update: %s", equipmentDto));
    }

    @Override
    public ResponseEquipmentDto get(Long id) {
        return mapper.mapToResponseEquipmentDto(getById(id));
    }

    @Override
    public List<ResponseShortEquipmentDto> getAll(Long buildingId) {
        return repository.findAllByBuildingId(buildingId).stream()
                .map(mapper::mapToResponseShortEquipmentDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment with id=%s not found for delete", id));
    }

    @Override
    public Equipment getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Equipment with id=%s not found", id)));
    }

    private boolean getByPredicate(NewEquipmentDto equipmentDto) {
        if (equipmentDto.getRoom() != null) {
            return repository.existsByEquipmentLibraryIdAndBuildingIdAndRoom(equipmentDto.getEquipmentLibraryId()
                                                                           , equipmentDto.getBuildingId()
                                                                           , equipmentDto.getRoom());
        }
        return repository.existsByEquipmentLibraryIdAndBuildingId(equipmentDto.getEquipmentLibraryId()
                                                                , equipmentDto.getBuildingId());
    }
}