package ru.nabokovsg.measurementqc.dto.integration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Сведения библиотеки дефектов и типов ремонтов элементов оборудования")
public class LibraryDto {

    @Schema(description = "Наименование дефекта")
    private String defectName;
    @Schema(description = "Указание недопустимости дефекта")
    private Boolean unacceptable;
    @Schema(description = "Использовать дефект для расчета остаточной толщины")
    private Boolean useCalculateThickness;
    @Schema(description = "Наименование типа ремонта")
    private String repairName;
    @Schema(description = "Измеряемые параметры ремонта элемента")
    private List<MeasurementParameterLibraryDto> measuredParameters;
}