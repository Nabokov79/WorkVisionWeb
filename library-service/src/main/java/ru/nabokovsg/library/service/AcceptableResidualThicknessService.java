package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.acceptableResidualThickness.NewAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.UpdateAcceptableResidualThicknessDto;

import java.util.List;

public interface AcceptableResidualThicknessService {

    ResponseAcceptableResidualThicknessDto save(NewAcceptableResidualThicknessDto thicknessDto);

    ResponseAcceptableResidualThicknessDto update(UpdateAcceptableResidualThicknessDto thicknessDto);

    List<ResponseAcceptableResidualThicknessDto> getAll(Long equipmentLibraryId);

    void delete(Long id);
}