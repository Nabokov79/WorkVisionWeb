package ru.nabokovsg.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.library.dto.acceptableMetalHardness.AcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableResidualThickness.ResponseAcceptableResidualThicknessDto;
import ru.nabokovsg.library.dto.integration.LibraryDto;
import ru.nabokovsg.library.service.MeasurementQCIntegrationService;

@RestController
@RequestMapping(
        value = "/library",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные библиотеки",
        description="API для работы с данными бибилиотеки, передаваемые другим сервисам")
@Slf4j
public class MeasurementQCIntegrationController {

    private final MeasurementQCIntegrationService service;

    @Operation(summary = "Получить наименование типа ремонта")
    @GetMapping("/repair/{id}")
    public ResponseEntity<LibraryDto> getRepairLibrary(@PathVariable
                                                    @Parameter(description = "Идентификатор типа ремонта") Long id) {
        log.info(String.format("GET RepairLibrary by id=%s", id));
        return ResponseEntity.ok().body(service.getRepairLibrary(id));
    }

    @Operation(summary = "Получить наименование типа ремонта")
    @GetMapping("/defect/{id}")
    public ResponseEntity<LibraryDto> getDefectLibrary(@PathVariable
                                                    @Parameter(description = "Идентификатор типа дефекта") Long id) {
        log.info(String.format("GET DefectLibrary by id=%s", id));
        return ResponseEntity.ok().body(service.getDefectLibrary(id));
    }

    @Operation(summary = "Получить наименование типа ремонта")
    @GetMapping("/acceptable/thickness")
    public ResponseEntity<ResponseAcceptableResidualThicknessDto> getAcceptableResidualThickness(
                          @RequestParam(name = "equipmentLibraryId")
                          @Parameter(description = "Идентификатор типа оборудования") Long equipmentLibraryId
                        , @RequestParam(name = "elementLibraryId")
                          @Parameter(description = "Идентификатор типа элемента оборудования") Long elementLibraryId
                        , @RequestParam(name = "partElementLibraryId")
                          @Parameter(description = "Идентификатор типа подэлемента оборудования") Long partElementLibraryId
                        , @RequestParam(name = "standardSize")
                          @Parameter(description = "Типоразмер элемента, поэлемента") String standardSize) {
        log.info(String.format("GET AcceptableResidualThickness by: " +
                        "equipmentLibraryId=%s, elementLibraryId=%s, partElementLibraryId=%s, standardSize=%s",
                         equipmentLibraryId, elementLibraryId, partElementLibraryId, standardSize));
        return ResponseEntity.ok().body(service.getAcceptableResidualThickness(equipmentLibraryId
                                                                             , elementLibraryId
                                                                             , partElementLibraryId
                                                                             , standardSize));
    }

    @Operation(summary = "Получить наименование типа ремонта")
    @GetMapping("/acceptable/hardness")
    public ResponseEntity<AcceptableMetalHardnessDto> getAcceptableMetalHardness(
                          @RequestParam(name = "equipmentLibraryId")
                          @Parameter(description = "Идентификатор типа оборудования") Long equipmentLibraryId
                        , @RequestParam(name = "elementLibraryId")
                          @Parameter(description = "Идентификатор типа элемента оборудования") Long elementLibraryId
                        , @RequestParam(name = "partElementLibraryId")
                          @Parameter(description = "Идентификатор типа подэлемента оборудования") Long partElementLibraryId
                        , @RequestParam(name = "standardSize")
                          @Parameter(description = "Типоразмер элемента, поэлемента") String standardSize) {
        log.info(String.format("GET AcceptableResidualThickness by: " +
                        "equipmentLibraryId=%s, elementLibraryId=%s, partElementLibraryId=%s, standardSize=%s",
                         equipmentLibraryId, elementLibraryId, partElementLibraryId, standardSize));
        return ResponseEntity.ok().body(service.getAcceptableMetalHardness(equipmentLibraryId
                                                                         , elementLibraryId
                                                                         , partElementLibraryId
                                                                         , standardSize));
    }
}