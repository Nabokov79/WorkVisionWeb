package ru.nabokovsg.library.mapper;

import org.mapstruct.Mapper;
import ru.nabokovsg.library.dto.acceptableMetalHardness.AcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.integration.LibraryDto;
import ru.nabokovsg.library.model.AcceptableMetalHardness;
import ru.nabokovsg.library.model.AcceptableResidualThickness;
import ru.nabokovsg.library.model.DefectLibrary;
import ru.nabokovsg.library.model.RepairLibrary;

@Mapper(componentModel = "spring")
public interface MeasurementQCIntegrationMapper {

    LibraryDto mapRepairLibrary(RepairLibrary repairLibrary);

    LibraryDto mapDefectLibrary(DefectLibrary defectLibrary);

    ResponseAcceptableResidualThicknessDto mapToAcceptableResidualThicknessDto(AcceptableResidualThickness thickness);

    AcceptableMetalHardnessDto mapToAcceptableMetalHardnessDto(AcceptableMetalHardness hardness);
}