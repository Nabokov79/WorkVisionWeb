package ru.nabokovsg.library.service;

import ru.nabokovsg.library.dto.acceptableMetalHardness.AcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.integration.LibraryDto;

public interface MeasurementQCIntegrationService {

    LibraryDto getRepairLibrary(Long id);

    LibraryDto getDefectLibrary(Long id);

    ResponseAcceptableResidualThicknessDto getAcceptableResidualThickness(Long equipmentLibraryId
                                                                        , Long elementLibraryId
                                                                        , Long partElementLibraryId
                                                                        , String standardSize);

    AcceptableMetalHardnessDto getAcceptableMetalHardness(Long equipmentLibraryId
                                                        , Long elementLibraryId
                                                        , Long partElementLibraryId
                                                        , String standardSize);
}
