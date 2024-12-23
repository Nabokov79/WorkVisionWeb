package ru.nabokovsg.measurementqc.dto.equipment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class EquipmentDto {

    private Long equipmentId;
    private Long equipmentLibraryId;
    private Long elementId;
    private Long elementLibraryId;
    private String elementName;
    private Long partElementId;
    private Long partElementLibraryId;
    private String partElementName;
    private String standardSizeString;
    private Double thickness;
    private Integer minDiameter;
    private Double minThickness;
    private Integer maxDiameter;
    private Double maxThickness;
}