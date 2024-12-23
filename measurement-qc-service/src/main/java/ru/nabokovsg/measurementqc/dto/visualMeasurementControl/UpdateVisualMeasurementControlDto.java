package ru.nabokovsg.measurementqc.dto.visualMeasurementControl;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.nabokovsg.equipmentDiagnosedQCL.dto.measuredParameter.UpdateMeasuredParameterDto;
import ru.nabokovsg.measurementqc.dto.measuredParameter.UpdateMeasuredParameterDto;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Изменить результаты измерения дефекта сварного соединения")
public class UpdateVisualMeasurementControlDto {

    @Schema(description = "Идентификатор")
    @NotNull(message = "id should not be null")
    @Positive(message = "id can only be positive")
    private Long id;
    @Schema(description = "Идентификатор записи в журнале обследований")
    @NotNull(message = "workJournalId should not be null")
    @Positive(message = "workJournalId can only be positive")
    private Long workJournalId;
    @Schema(description = "Порядковый номер сварного соединения")
    @NotNull(message = "weldedJointNumber should not be null")
    @Positive(message = "weldedJointNumber can only be positive")
    private Integer weldedJointNumber;
    @Schema(description = "Типоразмер соединяемых элементов")
    @NotBlank(message = "standardSize should not be blank")
    private String standardSize;
    @Schema(description = "Идентификатор дефекта")
    private Long defectId;
    @Schema(description = "Координаты расположения дефекта")
    @NotBlank(message = "coordinates should not be blank")
    private String coordinates;
    @Schema(description = "Оценка качества сварного соединения")
    private String qualityAssessment;
    @Schema(description = "Измеренные параметры дефекта элемента")
    private List<@Valid UpdateMeasuredParameterDto> measuredParameters;
}