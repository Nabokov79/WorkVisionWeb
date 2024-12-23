package ru.nabokovsg.measurementqc.controller.qualityControl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.NewUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.ResponseUltrasonicControlDto;
import ru.nabokovsg.measurementqc.dto.ultrasonicControl.UpdateUltrasonicControlDto;
import ru.nabokovsg.measurementqc.service.qualityControl.UltrasonicControlService;

import java.util.List;

@RestController
@RequestMapping(
        value =  "/WorkVisionWeb/control/ultrasonic",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name="Ультразвуковой контроль сварного соединения",
        description="API для работы с результатами ультразвукового контроля сварного соединения")
public class UltrasonicControlController {

    private final UltrasonicControlService service;

    @Operation(summary = "Добавить результат ультразвукового контроля")
    @PostMapping
    public ResponseEntity<ResponseUltrasonicControlDto> save(@RequestBody @Valid
                                                                    @Parameter(name = "Результат ультразвукового контроля")
                                                             NewUltrasonicControlDto defectDto) {
        return ResponseEntity.ok().body(service.save(defectDto));
    }

    @Operation(summary = "Изменить результат ультразвукового контроля")
    @PatchMapping
    public ResponseEntity<ResponseUltrasonicControlDto> update(@RequestBody @Valid
                                                                      @Parameter(name = "Результат ультразвукового контроля")
                                                               UpdateUltrasonicControlDto defectDto) {
        return ResponseEntity.ok().body(service.update(defectDto));
    }

    @Operation(summary = "Получить результат ультразвукового контроля")
    @GetMapping("{id}")
    public ResponseEntity<ResponseUltrasonicControlDto> get(@PathVariable(name = "id") @NotNull @Positive
                                                                   @Parameter(name = "Идентификатор") Long id) {
        return ResponseEntity.ok().body(service.get(id));
    }

    @Operation(summary = "Получить результаты ультразвукового контроля по идентификатору записи рабочего журнала")
    @GetMapping
    public ResponseEntity<List<ResponseUltrasonicControlDto>> getAll(
            @RequestParam(name = "id") @NotNull @Positive
            @Parameter(name = "Идентификатор записи рабочего журнала") Long workJournalId) {
        return ResponseEntity.ok().body(service.getAll(workJournalId));
    }

    @Operation(summary = "Удалить результат ультразвукового контроля")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Дефект успешно удален.");
    }
}