package ru.nabokovsg.equipment.dto.recommendationSpecialist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Schema(description = "Данные рекомендации сотрудника")
public class ResponseRecommendationSpecialistDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Элемент оборудования")
    private String elementName;
    @Schema(description = "Подэлемент оборудования")
    private String partElementName;
    @Schema(description = "Рекомендация")
    private String recommendation;
}