package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.library.dto.repairLibrary.NewRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.ResponseShortRepairLibraryDto;
import ru.nabokovsg.library.dto.repairLibrary.UpdateRepairLibraryDto;
import ru.nabokovsg.library.model.ParameterCalculationType;
import ru.nabokovsg.library.model.RepairLibrary;

@Mapper(componentModel = "spring")
public interface RepairLibraryMapper {

    @Mapping(source = "calculation", target = "calculation")
    @Mapping(target = "id", ignore = true)
    RepairLibrary mapToTypeRepairLibrary(NewRepairLibraryDto repairDto
                                           , ParameterCalculationType calculation);

    @Mapping(source = "calculation", target = "calculation")
    @Mapping(source = "repairDto.id", target = "id")
    RepairLibrary mapToUpdateTypeRepairLibrary(UpdateRepairLibraryDto repairDto
                                                 , ParameterCalculationType calculation);

    ResponseRepairLibraryDto mapToResponseTypeRepairLibraryDto(RepairLibrary repair);

    ResponseShortRepairLibraryDto mapToResponseShortTypeRepairLibraryDto(RepairLibrary repair);
}