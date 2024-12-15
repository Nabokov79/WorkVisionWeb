package ru.nabokovsg.library.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acceptable_residual_thickness")
public class AcceptableResidualThickness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_library_id")
    private Long equipmentLibraryId;
    @Column(name = "element_library_id")
    private Long elementLibraryId;
    @Column(name = "part_element_library_id")
    private Long partElementLibraryId;
    @Column(name = "standard_size_string")
    private String standardSizeString;
    @Column(name = "min_diameter")
    private Integer minDiameter;
    @Column(name = "min_thickness")
    private Double minThickness;
    @Column(name = "max_diameter")
    private Integer maxDiameter;
    @Column(name = "max_thickness")
    private Double maxThickness;
    @Column(name = "acceptable_thickness")
    private Double acceptableThickness;
    @Column(name = "acceptable_percent")
    private Integer acceptablePercent;
    @Column(name = "measurement_error")
    private Float measurementError;
}