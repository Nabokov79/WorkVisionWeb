package ru.nabokovsg.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.library.dto.partElementLibrary.NewPartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.ResponsePartElementLibraryDto;
import ru.nabokovsg.library.dto.partElementLibrary.UpdatePartElementLibraryDto;
import ru.nabokovsg.library.exceptions.BadRequestException;
import ru.nabokovsg.library.exceptions.NotFoundException;
import ru.nabokovsg.library.mapper.PartElementLibraryMapper;
import ru.nabokovsg.library.model.ElementLibrary;
import ru.nabokovsg.library.model.PartElementLibrary;
import ru.nabokovsg.library.repository.PartElementLibraryRepository;

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