package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;

import java.util.List;

public interface AcceptableMetalHardnessService {

    ResponseAcceptableMetalHardnessDto save(NewAcceptableMetalHardnessDto hardnessDto);

    ResponseAcceptableMetalHardnessDto update(UpdateAcceptableMetalHardnessDto hardnessDto);

    List<ResponseAcceptableMetalHardnessDto> getAll(Long equipmentLibraryId);

    void delete(Long id);
}