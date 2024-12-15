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
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.NewAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.ResponseAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.dto.acceptableDeviationsGeodesy.UpdateAcceptableDeviationsGeodesyDto;
import ru.nabokovsg.library.service.AcceptableDeviationsGeodesyService;

import java.util.List;

@RestController
@RequestMapping(
        value = "/WorkVisionWeb/library/geodesy",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Данные допустимых значений отклонений геодезических измерений",
        description="API для работы с данными допустимых значений отклонений геодезических измерений")
public class AcceptableDeviationsGeodesyController {

    private final AcceptableDeviationsGeodesyService service;

    @Operation(summary = "Добавить допустимое значение отклонения")
    @PostMapping
    public ResponseEntity<ResponseAcceptableDeviationsGeodesyDto> save(
            @RequestBody @Valid
            @Parameter(name = "Допустимое значение отклонения") NewAcceptableDeviationsGeodesyDto geodesyDto) {
        return ResponseEntity.ok().body(service.save(geodesyDto));
    }

    @Operation(summary = "Изменение допустимое значение отклонения")
    @PatchMapping
    public ResponseEntity<ResponseAcceptableDeviationsGeodesyDto> update(
            @RequestBody @Valid
            @Parameter(name = "Допустимое значение отклонения") UpdateAcceptableDeviationsGeodesyDto geodesyDto) {
        return ResponseEntity.ok().body(service.update(geodesyDto));
    }

    @Operation(summary = "Получить допустимые значения отклонений")
    @GetMapping
    public ResponseEntity<List<ResponseAcceptableDeviationsGeodesyDto>> getAll(
                                @RequestParam(name = "id") @NotNull @Positive
                                @Parameter(description = "Идентификатор типа оборудования") Long equipmentLibraryId) {
        return ResponseEntity.ok().body(service.getAll(equipmentLibraryId));
    }

    @Operation(summary = "Удалить допустимое значение отклонения")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable @NotNull @Positive @Parameter(name = "Идентификатор") Long id) {
        service.delete(id);
        return ResponseEntity.ok("Допустимое значение отклонения успешно удалено.");
    }
}