package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.nabokovsg.library.dto.defectLibrary.NewDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseShortDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.UpdateDefectLibraryDto;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.ParameterCalculationType;

@Mapper(componentModel = "spring")
public interface DefectLibraryMapper {

    @Mapping(target = "measuredParameters", ignore = true)
    DefectLibrary mapToTypeDefectLibrary(NewDefectLibraryDto defectDto);

    @Mapping(target = "measuredParameters", ignore = true)
    void mapToUpdateTypeDefectLibrary(@MappingTarget DefectLibrary defect, UpdateDefectLibraryDto defectDto);

    void mapWithParameterCalculationType(@MappingTarget DefectLibrary defect, ParameterCalculationType calculation);

    ResponseDefectLibraryDto mapToResponseTypeDefectLibraryDto(DefectLibrary defect);

    ResponseShortDefectLibraryDto mapToResponseShortTypeDefectLibraryDto(DefectLibrary defect);
}