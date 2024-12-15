package ru.nabokovsg.company.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nabokovsg.company.dto.structuralDivision.ResponseStructuralDivisionDto;
import ru.nabokovsg.company.service.StructuralDivisionService;

@RestController
@RequestMapping(
        value = "/division",
        consumes = MediaType.ALL_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
@Tag(name="Сведения о структурном подразделении",
        description="API для получения сведений о структурных подразделениях предприятия" +
                " для добавления записи в журнал выполненных работ")
public class StructuralDivisionController {

    private final StructuralDivisionService service;

    @Operation(summary = "Получить данные структурного подразделения по идентификаторам")
    @GetMapping
    public ResponseEntity<ResponseStructuralDivisionDto> get(
            @RequestParam(name = "heatSupplyAreaId", required = false)
            @Parameter(description = "Идентификатор района теплоснабжения") Long heatSupplyAreaId
          , @RequestParam(name = "exploitationRegionId", required = false)
            @Parameter(description = "Идентификатор эксплуатационного участка") Long exploitationRegionId
          , @RequestParam(name = "addressId") @NotNull @Positive
            @Parameter(description = "Идентификатор адреса") Long addressId) {
        return ResponseEntity.ok().body(service.get(heatSupplyAreaId, exploitationRegionId, addressId));
    }
}