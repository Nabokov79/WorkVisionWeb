package ru.nabokovsg.library.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StandardSize {

    private Double thickness;
    private Integer minDiameter;
    private Double minThickness;
    private Integer maxDiameter;
    private Double maxThickness;
}