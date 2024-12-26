package ru.nabokovsg.measurementqc.controller.measurement;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.NewCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.ResponseCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.dto.completedRepairMeasurement.UpdateCompletedRepairMeasurementDto;
import ru.nabokovsg.measurementqc.service.measurement.CompletedRepairMeasurementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/measurement/repair/qc",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Выполненные ремонты элементов, подэлементов оборудования",
        description="API для работы с данными выполненных ремонтов элементов, подэлементов оборудования")
public class CompletedRepairMeasurementController {

    private final CompletedRepairMeasurementService service;

    @Operation(summary = "Добавить выполненный ремонт элемента")
    @PostMapping
    public ResponseEntity<ResponseCompletedRepairMeasurementDto> save(
                            @RequestBody @Valid
                            @Parameter(description = "Выполненный ремонт") NewCompletedRepairMeasurementDto repairDto) {
        return ResponseEntity.ok().body(service.save(repairDto));
    }

    @Operation(summary = "Изменить выполненный ремонт элемента")
    @PatchMapping
    public ResponseEntity<ResponseCompletedRepairMeasurementDto> update(
            @RequestBody @Valid
            @Parameter(description = "Выполненный ремонт") UpdateCompletedRepairMeasurementDto repairDto) {
        return ResponseEntity.ok().body(service.update(repairDto));
    }

    @Operation(summary = "Получить выполненный ремонт элемента")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCompletedRepairMeasurementDto> get(
            @PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить выполненные ремонты элементов оборудования по идентификатору оборудования")
    @GetMapping("/{equipmentId}")
    public ResponseEntity<List<ResponseCompletedRepairMeasurementDto>> getAll(
                                            @PathVariable(name = "equipmentId") @NotNull @Positive
                                            @Parameter(description = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить ремонт элемента")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Выполненный ремонт элемента оборудования успешно удален.");
    }
}