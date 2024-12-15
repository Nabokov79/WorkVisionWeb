package ru.nabokovsg.library.dto.acceptableMetalHardness;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные допустимых толщин элементов оборудования")
public class ResponseAcceptableMetalHardnessDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Идентификатор типа оборудования")
    private Long equipmentTypeId;
    @Schema(description = "Идентификатор элемента оборудования")
    private Long elementTypeId;
    @Schema(description = "Идентификатор подэлемента оборудования")
    private Long partElementTypeId;
    @Schema(description = "Минимальная допустимая твердость металла элемента")
    private Integer minHardness;
    @Schema(description = "Максимальная допустимая твердость металла элемента")
    private Integer maxHardness;
    @Schema(description = "Допустимая погрешность измерения")
    private Float measurementError;
}