package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.elementLibrary.NewElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.ResponseElementLibraryDto;
import ru.nabokovsg.library.dto.elementLibrary.UpdateElementLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.ElementLibraryMapper;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.EquipmentLibrary;
import ru.nabokovsg.library.model.PartElementLibrary;
import ru.nabokovsg.library.repository.ElementLibraryRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ElementLibraryServiceImpl implements ElementLibraryService {

    private final ElementLibraryRepository repository;
    private final ElementLibraryMapper mapper;
    private final EquipmentLibraryService equipmentTypeService;
    private final PartElementLibraryService partElementLibraryService;

    @Override
    public ResponseElementLibraryDto save(NewElementLibraryDto elementDto) {
        if (repository.existsByEquipmentLibraryIdAndElementName(elementDto.getEquipmentId()
                                                           , elementDto.getElementName())) {
            throw new BadRequestException(String.format("Element by equipmentTypeId=%s and elementName=%s is found"
                                                                                    , elementDto.getEquipmentId()
                                                                                    , elementDto.getElementName()));
        }
        return mapper.mapToResponseElementLibraryDto(
                repository.save(mapper.mapToElementLibrary(elementDto
                                                       , equipmentTypeService.getById(elementDto.getEquipmentId()))));
    }

    @Override
    public ResponseElementLibraryDto update(UpdateElementLibraryDto elementDto) {
        if (repository.existsById(elementDto.getId())) {
            return mapper.mapToResponseElementLibraryDto(
                    repository.save(mapper.mapToUpdateElementLibrary(elementDto
                                                        , equipmentTypeService.getById(elementDto.getEquipmentId())))
            );
        }
        throw new NotFoundException(String.format("ElementType with id=%s not found for update", elementDto.getId()));
    }

    @Override
    public void copy(EquipmentLibrary equipmentLibrary, Set<ElementLibrary> elements) {
        Map<String, Set<PartElementLibrary>> partsElement = new HashMap<>(elements.size());
        elements.forEach(element -> partsElement.put(element.getElementName(), element.getPartsElement()));
        List<ElementLibrary> elementsCopies = repository.saveAll(equipmentLibrary.getElements()
                                                               .stream()
                                                               .map(element -> mapper.mapToCopyElementLibrary(element
                                                                                                   , equipmentLibrary))
                                                               .toList());
        elementsCopies.forEach(element -> {
            if (element.getPartsElement() != null) {
                partElementLibraryService.copy(element, partsElement.get(element.getElementName()));
            }
        });
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