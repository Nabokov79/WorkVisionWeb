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
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.NewGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.dto.geodesicMeasurements.ResponseGeodesicMeasurementsDto;
import ru.nabokovsg.measurementqc.service.measurement.GeodesicMeasurementsService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/measurement/geodesy/qc",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Измерения геодезической съемки(нивелировании) оборудования",
        description="API для работы с данными измерений геодезической съемки(нивелировании) оборудования")
public class GeodesicMeasurementsController {

    private final GeodesicMeasurementsService service;

    @Operation(summary = "Добавить данные геодезический съемки оборудования")
    @PostMapping
    public ResponseEntity<List<ResponseGeodesicMeasurementsDto>> save(
            @RequestBody @Valid
            @Parameter(name = "Данные измерений геодезической съемки") NewGeodesicMeasurementsDto measurementDto) {
        return ResponseEntity.ok().body(service.save(measurementDto));
    }

    @Operation(summary = "Получить данные геодезических измерений по идентификатору записи журнала задач")
    @GetMapping("/{equipmentId}")
    public ResponseEntity<List<ResponseGeodesicMeasurementsDto>> getAll(
                                            @PathVariable(name = "equipmentId") @NotNull @Positive
                                            @Parameter(description = "Идентификатор оборудования") Long equipmentId) {
        return ResponseEntity.ok().body(service.getAll(equipmentId));
    }

    @Operation(summary = "Удалить результаты геодезической съемки")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Геодезическое измерение успешно удалено.");
    }
}