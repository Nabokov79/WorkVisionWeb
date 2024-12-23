package ru.nabokovsg.measurementqc.dto.identifiedDefect;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Результаты измерения дефекта элемента, подэлемента оборудования")
public class UpdateIdentifiedDefectMeasurementDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор диагностируемого оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор элемента")
    private Long elementId;
    @Schema(description = "Идентификатор подэлемента")
    private Long partElementId;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Измеренные параметры дефекта элемента")
    private List<@Valid UpdateMeasuredParameterDto> measuredParameters;
}