package ru.nabokovsg.library.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.equipmentLibrary.NewEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.ResponseEquipmentLibraryDto;
import ru.nabokovsg.library.dto.equipmentLibrary.UpdateEquipmentLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.EquipmentLibraryMapper;
import ru.nabokovsg.library.model.EquipmentLibrary;
import ru.nabokovsg.library.model.QEquipmentLibrary;
import ru.nabokovsg.library.repository.EquipmentLibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentLibraryServiceImpl implements EquipmentLibraryService {

    private final EquipmentLibraryRepository repository;
    private final EquipmentLibraryMapper mapper;
    private final ElementLibraryService elementLibraryService;
    private final EntityManager em;

    @Override
    public ResponseEquipmentLibraryDto save(NewEquipmentLibraryDto equipmentDto) {
        if (equipmentDto.getCopy()) {
            return mapper.mapResponseEquipmentLibraryDto(copy(equipmentDto));
        }
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

    private EquipmentLibrary copy(NewEquipmentLibraryDto equipmentDto) {
        if (equipmentDto.getEquipmentLibraryId() <= 0) {
            throw new BadRequestException(
                    String.format("Equipment library id should not be null and can only be positive" +
                            "                    : equipmentLibraryId=%s", equipmentDto.getEquipmentLibraryId()));
        }
        EquipmentLibrary equipment = getById(equipmentDto.getEquipmentLibraryId());
        EquipmentLibrary equipmentCopy = repository.save(mapper.mapToCopyEquipmentLibrary(equipment));
        elementLibraryService.copy(equipmentCopy, equipment.getElements());
        return equipmentCopy;
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