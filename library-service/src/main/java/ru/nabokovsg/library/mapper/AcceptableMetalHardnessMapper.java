package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.model.StandardSize;

@Mapper(componentModel = "spring")
public interface AcceptableMetalHardnessMapper {

    AcceptableMetalHardness mapToAcceptableHardness(NewAcceptableMetalHardnessDto hardnessDto);

    AcceptableMetalHardness mapToUpdateAcceptableHardness(UpdateAcceptableMetalHardnessDto hardnessDto);

    ResponseAcceptableMetalHardnessDto mapToResponseAcceptableMetalHardnessDto(AcceptableMetalHardness hardness);

    void mapToStandardSizeString(@MappingTarget AcceptableMetalHardness acceptableMetalHardness, String standardSizeString);

    @Mapping(source = "acceptableMetalHardness.minAcceptableDiameter", target = "minDiameter")
    @Mapping(source = "acceptableMetalHardness.minAcceptableThickness", target = "minThickness")
    StandardSize mapToStandardSize(AcceptableMetalHardness acceptableMetalHardness);
}