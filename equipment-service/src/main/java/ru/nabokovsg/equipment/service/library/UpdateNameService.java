package ru.nabokovsg.equipment.service.library;

import ru.nabokovsg.equipment.model.library.ElementLibrary;
import ru.nabokovsg.equipment.model.library.PartElementLibrary;

public interface UpdateNameService {

    void updateElementName(ElementLibrary elementLibrary);

    void updatePartElementName(PartElementLibrary partElementLibrary);
}
