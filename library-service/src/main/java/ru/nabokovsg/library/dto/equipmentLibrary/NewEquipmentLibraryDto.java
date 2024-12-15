package ru.nabokovsg.library.dto.equipmentLibrary;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Данные для добавления информации о виде оборудования")
public class NewEquipmentLibraryDto {

    @Schema(description = "Наименование")
    @NotBlank(message = "equipmentName should not be blank")
    private String equipmentName;
    @Schema(description = "Объем")
    private Integer volume;
    @Schema(description = "Модель")
    private String model;
    @Schema(description = "Положение оборудования")
    private String orientation;
    @Schema(description = "Идентификатор вида оборудования")
    private Long equipmentLibraryId;
    @Schema(description = "Добавить путем копирования сохраненного оборудования")
    @NotNull(message = "copy should not be null")
    private Boolean copy;
}