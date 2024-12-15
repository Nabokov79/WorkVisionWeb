package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nabokovsg.library.dto.defectLibrary.NewDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.ResponseShortDefectLibraryDto;
import ru.nabokovsg.library.dto.defectLibrary.UpdateDefectLibraryDto;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.ParameterCalculationType;

@Mapper(componentModel = "spring")
public interface DefectLibraryMapper {

    @Mapping(source = "calculation", target = "calculation")
    @Mapping(target = "id", ignore = true)
    DefectLibrary mapToTypeDefectLibrary(NewDefectLibraryDto defectDto
                                           , ParameterCalculationType calculation);

    @Mapping(source = "calculation", target = "calculation")
    @Mapping(source = "defectDto.id", target = "id")
    DefectLibrary mapToUpdateTypeDefectLibrary(UpdateDefectLibraryDto defectDto
                                                 , ParameterCalculationType calculation);

    ResponseDefectLibraryDto mapToResponseTypeDefectLibraryDto(DefectLibrary defect);

    ResponseShortDefectLibraryDto mapToResponseShortTypeDefectLibraryDto(DefectLibrary defect);
}