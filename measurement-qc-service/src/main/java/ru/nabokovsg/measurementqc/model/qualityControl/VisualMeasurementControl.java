package ru.nabokovsg.measurementqc.model.qualityControl;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.nabokovsg.measurementqc.model.measurement.MeasuredParameter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "visual_measurement_control")
public class VisualMeasurementControl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "work_journal_id")
    private Long workJournalId;
    @Column(name = "welded_joint_number")
    private Integer weldedJointNumber;
    @Column(name = "standard_size")
    private String standardSize;
    @Column(name = "defect_name")
    private String defectName;
    @Column(name = "coordinates")
    private String coordinates;
    @Column(name = "quality_assessment")
    private String qualityAssessment;
    @OneToMany(mappedBy = "visualMeasurementControl",
               orphanRemoval = true,
               cascade = CascadeType.REMOVE,
               fetch = FetchType.EAGER)
    private Set<MeasuredParameter> measuredParameters;
}