package ru.nabokovsg.library.dto.acceptableResidualThickness;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные допустимых толщин элементов оборудования")
public class ResponseAcceptableResidualThicknessDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Идентификатор элемента оборудования")
    private Long elementTypeId;
    @Schema(description = "Идентификатор подэлемента оборудования")
    private Long partElementTypeId;
    @Schema(description = "Типаразмер элемента, подэлемента")
    private String standardSizeString;
    @Schema(description = "Минимальная допустимая толщина стенки элемента")
    private Double acceptableThickness;
    @Schema(description = "Минимальная допустимая толщина стенки элемента в процентах")
    private Integer acceptablePercent;
    @Schema(description = "Допустимая погрешность измерения")
    private Float measurementError;
}