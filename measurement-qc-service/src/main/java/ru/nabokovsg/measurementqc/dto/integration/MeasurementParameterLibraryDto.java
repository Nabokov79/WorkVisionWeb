package ru.nabokovsg.measurementqc.dto.integration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Измеряемый параметр")
public class MeasurementParameterLibraryDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование параметра")
    private String parameterName;
    @Schema(description = "Максимальное допустимое значение параметра")
    private Double maxAllowedValue;
    @Schema(description = "Единица измерения параметра")
    private String unitMeasurement;
}