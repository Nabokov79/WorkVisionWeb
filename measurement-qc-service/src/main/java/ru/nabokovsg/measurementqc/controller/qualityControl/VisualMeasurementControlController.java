package ru.nabokovsg.measurementqc.controller.qualityControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.NewVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.ResponseVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.dto.visualMeasurementControl.UpdateVisualMeasurementControlDto;
import ru.nabokovsg.measurementqc.service.qualityControl.VisualMeasurementControlService;

import java.util.List;

@RestController
@RequestMapping(
        value =  "/WorkVisionWeb/control/visual",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Визуальный и измерительный контроль сварного соединения",
        description="API для работы с результатами визуального и измерительнго контроля сварного соединения")
public class VisualMeasurementControlController {

    private final VisualMeasurementControlService service;

    @Operation(summary = "Добавить данные результата измерения")
    @PostMapping
    public ResponseEntity<ResponseVisualMeasurementControlDto> save(@RequestBody
                                                                    @Parameter(name = "Данные измеренния дефекта")
                                                                    NewVisualMeasurementControlDto defectDto) {
        return ResponseEntity.ok().body(service.save(defectDto));
    }

    @Operation(summary = "Изменить данные результата измерения дефекта")
    @PatchMapping
    public ResponseEntity<ResponseVisualMeasurementControlDto> update(@RequestBody
                                                                      @Parameter(name = "Данные измеренния дефекта")
                                                                      UpdateVisualMeasurementControlDto defectDto) {
        return ResponseEntity.ok().body(service.update(defectDto));
    }

    @Operation(summary = "Получить измеренныый дефект сварного соединения")
    @GetMapping("{id}")
    public ResponseEntity<ResponseVisualMeasurementControlDto> get(@PathVariable(name = "id") @NotNull @Positive
                                                                          @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить данные измеренных дефектов по идентификатору записи рабочего журнала")
    @GetMapping("{workJournalId}")
    public ResponseEntity<List<ResponseVisualMeasurementControlDto>> getAll(
                                       @PathVariable(name = "workJournalId") @NotNull @Positive
                                       @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return ResponseEntity.ok().body(service.getAll(workJournalId));
    }

    @Operation(summary = "Удалить измеренный дефект")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Дефект успешно удален.");
    }
}