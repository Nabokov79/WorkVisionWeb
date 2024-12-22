package ru.nabokovsg.equipment.dto.remarkSpecialist;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Замечание по техническому состоянию элементов/подэлементов оборудования")
public class ResponseRemarkSpecialistDto {

    @Schema(description = "Идентификатор")
    private Long id;
    @Schema(description = "Наименование элемента")
    private String elementName;
    @Schema(description = "Наименование подэлемента")
    private String partElementName;
    @Schema(description = "Замечание по техническому состоянию")
    private String remark;
}