package ru.nabokovsg.measurementqc.dto.integration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "Сведения об оборудовании")
public class EquipmentDto {

    @Schema(description = "Идентификатор оборудования")
    private Long equipmentId;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentLibraryId;
    @Schema(description = "Идентификатор элемента оборудования")
    private Long elementId;
    @Schema(description = "Идентификатор типа элемента оборудования")
    private Long elementLibraryId;
    @Schema(description = "Наименование элемента оборудования")
    private String elementName;
    @Schema(description = "Идентификатор подэлемента элемента оборудования")
    private Long partElementId;
    @Schema(description = "Идентификатор типа подэлемента элемента оборудования")
    private Long partElementLibraryId;
    @Schema(description = "Наименование подэлемента элемента оборудования")
    private String partElementName;
    @Schema(description = "Типоразмер элемента или подэлемента оборудования")
    private String standardSize;
    @Schema(description = "Толщина элемента или подэлемента")
    private Double thickness;
    @Schema(description = "Минимальный диаметр элемента")
    private Integer minDiameter;
    @Schema(description = "Толщина при минимальном диаметре элемента")
    private Double minThickness;
    @Schema(description = "Максимальный диаметр элемента")
    private Integer maxDiameter;
    @Schema(description = "Толщина при максимальном диаметре элемента")
    private Double maxThickness;
}