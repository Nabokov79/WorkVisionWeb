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
import ru.nabokovsg.measurementqc.dto.identifiedDefect.NewIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.ResponseIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.dto.identifiedDefect.UpdateIdentifiedDefectMeasurementDto;
import ru.nabokovsg.measurementqc.service.measurement.IdentifiedDefectMeasurementService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/measurement/defect",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Обнаруженные дефекты элементов, подэлементов оборудования",
        description="API для работы с данными измерений дефектов элементов, подэлементов оборудования")
public class IdentifiedDefectMeasurementController {

    private final IdentifiedDefectMeasurementService service;

    @Operation(summary = "Добавить обнаруженный дефект")
    @PostMapping
    public ResponseEntity<ResponseIdentifiedDefectMeasurementDto> save(
            @RequestBody @Valid @Parameter(name = "Обнаруженный дефект") NewIdentifiedDefectMeasurementDto defectDto) {
        return ResponseEntity.ok().body(service.save(defectDto));
    }

    @Operation(summary = "Изменить обнаруженный дефект")
    @PatchMapping
    public ResponseEntity<ResponseIdentifiedDefectMeasurementDto> update(
            @RequestBody @Valid @Parameter(name = "Обнаруженный дефект") UpdateIdentifiedDefectMeasurementDto defectDto) {
        return ResponseEntity.ok().body(service.update(defectDto));
    }

    @Operation(summary = "Получить дефект элемента оборудования")
    @GetMapping("/{id}")
    public ResponseEntity<ResponseIdentifiedDefectMeasurementDto> get(
            @PathVariable @NotNull @Positive @Parameter(description = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить обнаруженный дефекты элементов(подэлементов) по идентификатору оборудования")
    @GetMapping("/{equipmentId}")
    public ResponseEntity<List<ResponseIdentifiedDefectMeasurementDto>> getAll(
                                            @PathVariable(name = "equipmentId") @NotNull @Positive
                                            @Parameter(description = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Обнаруженный дефект успешно удален.");
    }
}