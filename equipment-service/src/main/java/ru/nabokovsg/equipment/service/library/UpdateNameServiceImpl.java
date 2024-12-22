package ru.nabokovsg.equipment.service.library;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nabokovsg.equipment.mapper.library.UpdateNameMapper;
import ru.nabokovsg.equipment.model.equipment.EquipmentElement;
import ru.nabokovsg.equipment.model.equipment.EquipmentPartElement;
import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;
import ru.nabokovsg.equipment.repository.equipment.EquipmentElementRepository;
import ru.nabokovsg.equipment.repository.equipment.EquipmentPartElementRepository;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UpdateNameServiceImpl implements UpdateNameService {

    private final EquipmentElementRepository elementRepository;
    private final EquipmentPartElementRepository partElementRepository;
    private final UpdateNameMapper mapper;

    @Override
    public void updateElementName(ElementLibrary elementLibrary) {
        Set<EquipmentElement> elements = elementRepository.findAllByElementLibraryId(elementLibrary.getId());
       if (!elements.isEmpty()) {
           elements.forEach(element -> mapper.updateElementName(element, elementLibrary.getElementName()));
           elementRepository.saveAll(elements);
       }
    }

    @Override
    public void updatePartElementName(PartElementLibrary partElementLibrary) {
        Set<EquipmentPartElement> partElements =
                                        partElementRepository.findAllByPartElementLibraryId(partElementLibrary.getId());
        if (!partElements.isEmpty()) {
            partElements.forEach(partElement -> mapper.updatePartElementName(partElement
                                                                           , partElementLibrary.getPartElementName()));
            partElementRepository.saveAll(partElements);
        }
    }
}
