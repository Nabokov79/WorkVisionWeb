package ru.nabokovsg.library.controller;

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
import ru.nabokovsg.library.dto.acceptableMetalHardness.NewAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.ResponseAcceptableMetalHardnessDto;
import ru.nabokovsg.library.dto.acceptableMetalHardness.UpdateAcceptableMetalHardnessDto;
import ru.nabokovsg.library.service.AcceptableMetalHardnessService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/library/hardness",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные допустимых твердости металла элементов оборудования",
        description="API для работы с данными норм допустимой твердости металла элементов оборудования")
public class AcceptableMetalHardnessController {

    private final AcceptableMetalHardnessService service;

    @Operation(summary = "Добавить новые данные допустимой твердости металла элементов оборудования")
    @PostMapping
    public ResponseEntity<ResponseAcceptableMetalHardnessDto> save(
            @RequestBody @Valid
            @Parameter(name = "Данные допустимой твердости металла") NewAcceptableMetalHardnessDto hardnessDto) {
        return ResponseEntity.ok().body(service.save(hardnessDto));
    }

    @Operation(summary = "Изменение данных допустимой твердости металла элементов оборудования")
    @PatchMapping
    public ResponseEntity<ResponseAcceptableMetalHardnessDto> update(
            @RequestBody @Valid
            @Parameter(name = "Данные допустимой твердости металла") UpdateAcceptableMetalHardnessDto hardnessDto) {
        return ResponseEntity.ok().body(service.update(hardnessDto));
    }

    @Operation(summary = "Получить данные допустимых значений твердости металла элементов оборудования")
    @GetMapping
    public ResponseEntity<List<ResponseAcceptableMetalHardnessDto>> getAll(
                                @RequestParam(name = "id") @NotNull @Positive
                                @Parameter(description = "Идентификатор типа оборудования") Long equipmentLibraryId) {
        return ResponseEntity.ok().body(service.getAll(equipmentLibraryId));
    }

    @Operation(summary = "Удалить данные допустимой твердости металла элементов")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Данные допустимой твердости металла элементов оборудования успешно удалены.");
    }
}