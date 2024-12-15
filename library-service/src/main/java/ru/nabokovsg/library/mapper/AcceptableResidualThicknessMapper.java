package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.acceptableResidualThickness.NewAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.UpdateAcceptableResidualThicknessDto;
import ru.nabokovsg.library.model.AcceptableResidualThickness;

@Mapper(componentModel = "spring")
public interface AcceptableResidualThicknessMapper {

    AcceptableResidualThickness mapToAcceptableThickness(NewAcceptableResidualThicknessDto thicknessDto);

    AcceptableResidualThickness mapToUpdateAcceptableThickness(UpdateAcceptableResidualThicknessDto thicknessDto);

    ResponseAcceptableResidualThicknessDto mapToResponseAcceptableResidualThicknessDto(AcceptableResidualThickness thickness);

    void mapToStandardSizeString(@MappingTarget AcceptableResidualThickness acceptableThickness, String standardSizeString);
}