package ru.nabokovsg.equipment.service.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.dto.equipmentElement.NewEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.ResponseEquipmentElementDto;
import ru.nabokovsg.equipment.dto.equipmentElement.UpdateEquipmentElementDto;
import ru.nabokovsg.equipment.exceptions.BadRequestException;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.equipment.EquipmentElementMapper;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.repository.equipment.EquipmentElementRepository;
import ru.nabokovsg.equipment.service.library.ElementLibraryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EquipmentElementServiceImpl implements EquipmentElementService {

    private final EquipmentElementRepository repository;
    private final EquipmentElementMapper mapper;
    private final EquipmentService equipmentService;
    private final EquipmentPartElementService partElementService;
    private final ElementLibraryService libraryService;
    private final StandardSizeStringBuilder standardSizeStringBuilder;

    @Override
    public ResponseEquipmentElementDto save(NewEquipmentElementDto elementDto) {
        String standardSize = standardSizeStringBuilder.convertToString(mapper.mapToStandardSize(elementDto));
        EquipmentElement element = getByPredicate(elementDto, standardSize);
        if (element == null) {
            element = mapper.mapToElement(libraryService.getById(elementDto.getElementLibraryId())
                                        , equipmentService.getById(elementDto.getEquipmentId()));
            if (elementDto.getPartElementLibraryId() == null) {
                mapper.mapWithStandardSize(element, standardSize);
            }
            element = repository.save(element);
        } else if (elementDto.getPartElementLibraryId() == null){
            throw new BadRequestException(String.format("Equipment element: %s; is found", elementDto));
        }
        if (elementDto.getPartElementLibraryId() != null) {
            partElementService.save(element, elementDto.getPartElementLibraryId(), standardSize);
        }
        return mapper.mapToResponseEquipmentElementDto(element);
    }

    @Override
    public ResponseEquipmentElementDto update(UpdateEquipmentElementDto elementDto) {
        EquipmentElement element = get(elementDto.getId());
        String standardSize = standardSizeStringBuilder.convertToString(mapper.mapToUpdateStandardSize(elementDto));
        if (elementDto.getPartElementId() == null) {
            mapper.mapToUpdateElement(element
                              , standardSizeStringBuilder.convertToString(mapper.mapToUpdateStandardSize(elementDto)));
        } else {
            partElementService.update(element.getPartsElement(), elementDto.getPartElementId(), standardSize);
        }
        return mapper.mapToResponseEquipmentElementDto(repository.save(element));
    }

    @Override
    public EquipmentElement get(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Equipment element with id=%s not found", id)));
    }

    @Override
    public List<ResponseEquipmentElementDto> getAll(Long equipmentId) {
        return repository.findByEquipmentId(equipmentId)
                .stream()
                .map(mapper::mapToResponseEquipmentElementDto)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Equipment element with id=%s not found for delete", id));
    }

    private EquipmentElement getByPredicate(NewEquipmentElementDto elementDto, String standardSize) {
        if (elementDto.getPartElementLibraryId() == null) {
            return repository.findByEquipmentIdAndElementLibraryIdAndStandardSize(elementDto.getEquipmentId()
                                                                                , elementDto.getElementLibraryId()
                                                                                , standardSize);
        } else {
            return repository.findByEquipmentIdAndElementLibraryId(elementDto.getEquipmentId()
                                                                 , elementDto.getElementLibraryId());
        }
    }
}