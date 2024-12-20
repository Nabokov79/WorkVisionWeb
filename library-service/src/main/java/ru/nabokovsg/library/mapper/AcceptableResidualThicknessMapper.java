package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.library.dto.acceptableResidualThickness.NewAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.UpdateAcceptableResidualThicknessDto;
import ru.nabokovsg.library.model.AcceptableResidualThickness;
import ru.nabokovsg.library.model.StandardSize;

@Mapper(componentModel = "spring")
public interface AcceptableResidualThicknessMapper {

    AcceptableResidualThickness mapToAcceptableThickness(NewAcceptableResidualThicknessDto thicknessDto, String standardSize);

    AcceptableResidualThickness mapToUpdateAcceptableThickness(UpdateAcceptableResidualThicknessDto thicknessDto, String standardSize);

    ResponseAcceptableResidualThicknessDto mapToResponseAcceptableResidualThicknessDto(AcceptableResidualThickness thickness);

    StandardSize mapToStandardSize(NewAcceptableResidualThicknessDto thicknessDto);

    StandardSize mapToUpdateStandardSize(UpdateAcceptableResidualThicknessDto thicknessDto);
}