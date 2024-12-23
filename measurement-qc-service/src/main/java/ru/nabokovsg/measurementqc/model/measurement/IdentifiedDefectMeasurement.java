package ru.nabokovsg.measurementqc.model.measurement;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "identified_defect_measurements")
public class IdentifiedDefectMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "equipment_id")
    private Long equipmentId;
    @Column(name = "defect_id")
    private Long defectId;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "unacceptable")
    private Boolean unacceptable;
    @Column(name = "use_calculate_thickness")
    private Boolean useCalculateThickness;
    @Column(name = "quality_assessment")
    private String qualityAssessment;
    @Column(name = "element_id")
    private Long elementId;
    @Column(name = "element_name")
    private String elementName;
    @Column(name = "part_element_id")
    private Long partElementId;
    @Column(name = "part_element_name")
    private String partElementName;
    @OneToMany(mappedBy = "identifiedDefect",
               orphanRemoval = true,
               cascade = CascadeType.REMOVE,
               fetch = FetchType.EAGER)
    private Set<MeasuredParameter> measuredParameters;
}