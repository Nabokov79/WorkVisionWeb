package ru.nabokovsg.equipment.service.equipment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.exceptions.NotFoundException;
import ru.nabokovsg.equipment.mapper.equipment.EquipmentPartElementMapper;
import ru.nabokovsg.equipment.model.equipment.*;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;
import ru.nabokovsg.equipment.repository.equipment.EquipmentPartElementRepository;
import ru.nabokovsg.equipment.service.library.PartElementLibraryService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EquipmentPartElementServiceImpl implements EquipmentPartElementService {

    private final EquipmentPartElementRepository repository;
    private final EquipmentPartElementMapper mapper;
    private final PartElementLibraryService libraryService;

    @Override
    public void save(EquipmentElement element, Long partElementLibraryId, String standardSize) {
        PartElementLibrary partElementLibrary = libraryService.getById(partElementLibraryId);
        exists(element, partElementLibrary.getPartElementName(), standardSize);
        EquipmentPartElement partElement = repository.save(mapper.mapToEquipmentPartElement(element
                                                                                          , partElementLibrary
                                                                                          , standardSize));
        if (element.getPartsElement() == null) {
            element.setPartsElement(Set.of(partElement));
            return;
        }
        element.getPartsElement().add(partElement);
    }

    @Override
    public void update(Set<EquipmentPartElement> partsElement, Long partElementId, String standardSize) {
        List<EquipmentPartElement> parts = new ArrayList<>(1);
        partsElement.forEach(partElement -> {
            if (Objects.equals(partElement.getId(), partElementId)) {
                mapper.mapToUpdateEquipmentPartElement(partElement, standardSize);
                parts.add(partElement);
            }
        });
        repository.save(parts.get(0));
    }

    private void exists(EquipmentElement element, String partElementName, String standardSize) {
        if (element.getPartsElement() != null) {
            element.getPartsElement().forEach(partElement -> {
                if (partElement.getPartElementName().equals(partElementName)
                                                               && partElement.getStandardSize().equals(standardSize)) {
                    throw new NotFoundException(
                            String.format("EquipmentPartElement partElementName=%s, standardSize=%s is found"
                                    , partElementName
                                    , standardSize));
                }
            });
        }
    }
}