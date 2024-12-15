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
@Table(name = "library_measurement_parameters")
public class MeasurementParameterLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parameter_name")
    private String parameterName;
    @Column(name = "max_allowed_value")
    private Double maxAllowedValue;
    @Column(name = "unit_measurement")
    private String unitMeasurement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "defect_id", nullable = false)
    private DefectLibrary defect;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_id", nullable = false)
    private RepairLibrary elementRepair;
}