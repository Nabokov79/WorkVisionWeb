package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;
import ru.nabokovsg.library.model.AcceptableMetalHardness;

@Mapper(componentModel = "spring")
public interface AcceptableMetalHardnessMapper {

    AcceptableMetalHardness mapToAcceptableHardness(NewAcceptableMetalHardnessDto hardnessDto);

    AcceptableMetalHardness mapToUpdateAcceptableHardness(UpdateAcceptableMetalHardnessDto hardnessDto);

    ResponseAcceptableMetalHardnessDto mapToResponseAcceptableMetalHardnessDto(AcceptableMetalHardness hardness);

    void mapToStandardSizeString(@MappingTarget AcceptableMetalHardness acceptableMetalHardness, String standardSizeString);
}