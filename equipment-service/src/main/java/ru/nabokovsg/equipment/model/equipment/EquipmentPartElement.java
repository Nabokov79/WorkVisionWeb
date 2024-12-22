package ru.nabokovsg.equipment.model.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "equipment_parts_elements")
public class EquipmentPartElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_element_library_id")
    private Long partElementLibraryId;
    @Column(name = "part_element_name")
    private String partElementName;
    @Column(name = "standard_size")
    private String standardSize;
    @ManyToOne
    @JoinColumn(name = "element_id")
    @JsonIgnore
    private EquipmentElement element;
}