package ru.nabokovsg.equipment.service.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.dto.partElementLibrary.NewPartElementLibraryDto;
import ru.nabokovsg.equipment.dto.partElementLibrary.ResponsePartElementLibraryDto;
import ru.nabokovsg.equipment.dto.partElementLibrary.UpdatePartElementLibraryDto;
import ru.nabokovsg.equipment.exceptions.BadRequestException;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.library.PartElementLibraryMapper;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;
import ru.nabokovsg.equipment.repository.library.PartElementLibraryRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PartElementLibraryServiceImpl implements PartElementLibraryService {

    private final PartElementLibraryRepository repository;
    private final PartElementLibraryMapper mapper;
    private final ElementLibraryService elementService;

    @Override
    public ResponsePartElementLibraryDto save(NewPartElementLibraryDto partElementDto) {
        if (repository.existsByElementLibraryIdAndPartElementName(partElementDto.getElementId()
                                                                , partElementDto.getPartElementName())) {
            throw new BadRequestException(String.format("Element by elementId=%s and partElementName=%s is found"
                    , partElementDto.getElementId()
                    , partElementDto.getPartElementName()));
        }
        PartElementLibrary partElement = mapper.mapToPartElementLibrary(partElementDto);
        mapper.mapWithElementLibrary(partElement, elementService.getById(partElementDto.getElementId()));
        return mapper.mapToResponsePartElementLibraryDto(repository.save(partElement));
    }

    @Override
    public ResponsePartElementLibraryDto update(UpdatePartElementLibraryDto partElementDto) {
        PartElementLibrary partElement = mapper.mapToUpdatePartElementLibrary(getById(partElementDto.getId()), partElementDto);
        mapper.mapWithElementLibrary(partElement, elementService.getById(partElementDto.getElementId()));
        return mapper.mapToResponsePartElementLibraryDto(repository.save(partElement));
    }

    @Override
    public void copy(ElementLibrary element, Set<PartElementLibrary> partsElement) {
        List<PartElementLibrary> partsElementCopies = new ArrayList<>(partsElement.size());
        partsElement.forEach(part -> partsElementCopies.add(mapper.mapToCopyPartElementLibrary(part, element)));
        element.setPartsElement(new HashSet<>(repository.saveAll(partsElementCopies)));
    }

    @Override
    public List<ResponsePartElementLibraryDto> getAll(Long elementLibraryId) {
        return repository.findAllByElementLibraryId(elementLibraryId)
                         .stream()
                         .map(mapper::mapToResponsePartElementLibraryDto)
                         .toList();
    }

    @Override
    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundException(String.format("PartElement library with id=%s not found for delete", id));
    }

    @Override
    public PartElementLibrary getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format("PartElement library with id=%s not found for update", id)));
    }
}