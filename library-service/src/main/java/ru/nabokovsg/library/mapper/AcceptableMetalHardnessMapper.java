package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.model.StandardSize;

@Mapper(componentModel = "spring")
public interface AcceptableMetalHardnessMapper {

    AcceptableMetalHardness mapToAcceptableHardness(NewAcceptableMetalHardnessDto hardnessDto
                                                  , String standardSize);

    AcceptableMetalHardness mapToUpdateAcceptableHardness(UpdateAcceptableMetalHardnessDto hardnessDto
                                                        , String standardSize);

    ResponseAcceptableMetalHardnessDto mapToResponseAcceptableMetalHardnessDto(AcceptableMetalHardness hardness);

    @Mapping(source = "hardnessDto.minAcceptableDiameter", target = "minDiameter")
    @Mapping(source = "hardnessDto.minAcceptableThickness", target = "minThickness")
    StandardSize mapToStandardSize(NewAcceptableMetalHardnessDto hardnessDto);

    @Mapping(source = "hardnessDto.minAcceptableDiameter", target = "minDiameter")
    @Mapping(source = "hardnessDto.minAcceptableThickness", target = "minThickness")
    StandardSize mapToUpdateStandardSize(UpdateAcceptableMetalHardnessDto hardnessDto);
}