package ru.nabokovsg.equipment.service.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.equipment.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.equipment.exceptions.BadRequestException;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.library.ElementLibraryMapper;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.repository.library.ElementLibraryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementLibraryServiceImpl implements ElementLibraryService {

    private final ElementLibraryRepository repository;
    private final ElementLibraryMapper mapper;
    private final EquipmentLibraryService equipmentLibraryService;
    private final UpdateNameService updateNameService;

    @Override
    public ResponseElementLibraryDto save(NewElementLibraryDto elementDto) {
        if (repository.existsByEquipmentLibraryIdAndElementName(elementDto.getEquipmentLibraryId()
                                                           , elementDto.getElementName())) {
            throw new BadRequestException(String.format("Element by equipmentTypeId=%s and elementName=%s is found"
                                                                                    , elementDto.getEquipmentLibraryId()
                                                                                    , elementDto.getElementName()));
        }
        return mapper.mapToResponseElementLibraryDto(
                repository.save(mapper.mapToElementLibrary(elementDto
                                              , equipmentLibraryService.getById(elementDto.getEquipmentLibraryId()))));
    }

    @Override
    public ResponseElementLibraryDto update(UpdateElementLibraryDto elementDto) {
        if (repository.existsById(elementDto.getId())) {
            ElementLibrary elementLibrary = repository.save(mapper.mapToUpdateElementLibrary(elementDto
                                              , equipmentLibraryService.getById(elementDto.getEquipmentLibraryId())));
            updateNameService.updateElementName(elementLibrary);
            return mapper.mapToResponseElementLibraryDto( elementLibrary);
        }
        throw new NotFoundException(String.format("ElementType with id=%s not found for update", elementDto.getId()));
    }

    @Override
    public List<ResponseElementLibraryDto> getAll(Long equipmentLibraryId) {
        return repository.findAllByEquipmentLibraryId(equipmentLibraryId)
                         .stream()
                         .map(mapper::mapToResponseElementLibraryDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("Element type with id=%s not found for delete", id));
    }

    @Override
    public ElementLibrary getById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("ElementType with id=%s not found", id)));
    }
}