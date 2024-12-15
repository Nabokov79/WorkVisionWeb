package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.defectLibrary.NewDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseShortDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.UpdateDefectLibraryDto;
import ru.nabokovsg.library.model.DefectLibrary;

import java.util.List;

public interface DefectLibraryService {

    ResponseDefectLibraryDto save(NewDefectLibraryDto defectDto);

    ResponseDefectLibraryDto update(UpdateDefectLibraryDto defectDto);

    ResponseDefectLibraryDto get(Long id);

    List<ResponseShortDefectLibraryDto> getAll();

    void delete(Long id);

    DefectLibrary getById(Long id);
}