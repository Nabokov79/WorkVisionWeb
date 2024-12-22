package ru.nabokovsg.equipment.service.library;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.dto.equipmentLibrary.NewEquipmentLibraryDto;
import ru.nabokovsg.equipment.dto.equipmentLibrary.ResponseEquipmentLibraryDto;
import ru.nabokovsg.equipment.dto.equipmentLibrary.UpdateEquipmentLibraryDto;
import ru.nabokovsg.equipment.exceptions.BadRequestException;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.library.EquipmentLibraryMapper;
import ru.nabokovsg.equipment.model.library.EquipmentLibrary;
import ru.nabokovsg.equipment.model.library.QEquipmentLibrary;
import ru.nabokovsg.equipment.repository.library.EquipmentLibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EquipmentLibraryServiceImpl implements EquipmentLibraryService {

    private final EquipmentLibraryRepository repository;
    private final EquipmentLibraryMapper mapper;
    private final EntityManager em;
    private final EquipmentCopyingService copyingService;

    @Override
    public ResponseEquipmentLibraryDto save(NewEquipmentLibraryDto equipmentDto) {
        EquipmentLibrary equipment = getByPredicate(equipmentDto);
        if (equipment == null) {
            return mapper.mapResponseEquipmentLibraryDto(repository.save(mapper.mapToEquipmentLibrary(equipmentDto)));
        }
        throw new BadRequestException(String.format("Equipment library found : %s", equipmentDto));
    }

    @Override
    public ResponseEquipmentLibraryDto update(UpdateEquipmentLibraryDto equipmentDto) {
        if (repository.existsById(equipmentDto.getId())) {
            return mapper.mapResponseEquipmentLibraryDto(
                    repository.save(mapper.mapToUpdateEquipmentLibrary(equipmentDto)));
        }
        throw new NotFoundException(
                String.format("Equipment library with id=%s not found for update", equipmentDto.getId()));
    }

    @Override
    public ResponseEquipmentLibraryDto copy(NewEquipmentLibraryDto equipmentDto) {
        EquipmentLibrary equipment = repository.save(mapper.mapToEquipmentLibrary(equipmentDto));
        log.info(String.format("EquipmentLibrary : %s", equipment));
        copyingService.copy(equipment, equipmentDto.getEquipmentLibraryId());
        return mapper.mapResponseEquipmentLibraryDto(equipment);
    }

    @Override
    public ResponseEquipmentLibraryDto get(Long id) {
        return mapper.mapResponseEquipmentLibraryDto(getById(id));
    }

    @Override
    public List<ResponseEquipmentLibraryDto> getAll() {
        return repository.findAll()
                         .stream()
                         .map(mapper::mapResponseEquipmentLibraryDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment library with id=%s not found for delete", id));
    }

    @Override
    public EquipmentLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Equipment library with id=%s not found", id)));
    }

    public EquipmentLibrary getByPredicate(NewEquipmentLibraryDto equipmentDto) {
        QEquipmentLibrary equipment = QEquipmentLibrary.equipmentLibrary;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(equipment.equipmentName.eq(equipmentDto.getEquipmentName()));
        if (equipmentDto.getVolume() != null) {
            builder.and(equipment.volume.eq(equipmentDto.getVolume()));
        }
        if (equipmentDto.getOrientation() != null) {
            builder.and(equipment.orientation.eq(equipmentDto.getOrientation()));
        }
        if (equipmentDto.getModel() != null) {
            builder.and(equipment.model.eq(equipmentDto.getModel()));
        }
        return new JPAQueryFactory(em)
                        .from(equipment)
                        .select(equipment)
                        .where(builder)
                        .fetchOne();
    }
}