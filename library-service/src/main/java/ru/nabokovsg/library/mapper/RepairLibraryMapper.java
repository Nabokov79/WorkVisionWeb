package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.repairLibrary.NewRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseShortRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.UpdateRepairLibraryDto;
import ru.nabokovsg.library.model.ParameterCalculationType;
import ru.nabokovsg.library.model.RepairLibrary;

@Mapper(componentModel = "spring")
public interface RepairLibraryMapper {

    @Mapping(target = "measuredParameters", ignore = true)
    RepairLibrary mapToTypeRepairLibrary(NewRepairLibraryDto repairDto);

    @Mapping(target = "measuredParameters", ignore = true)
    void mapToUpdateTypeRepairLibrary(@MappingTarget RepairLibrary repair, UpdateRepairLibraryDto repairDto);

    void mapWithParameterCalculationType(@MappingTarget RepairLibrary repair, ParameterCalculationType calculation);

    ResponseRepairLibraryDto mapToResponseTypeRepairLibraryDto(RepairLibrary repair);

    ResponseShortRepairLibraryDto mapToResponseShortTypeRepairLibraryDto(RepairLibrary repair);
}