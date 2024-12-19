package ru.nabokovsg.equipment.model.library;

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
@Table(name = "elements_library")
public class ElementLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "element_name")
    private String elementName;
    @OneToMany(mappedBy = "elementLibrary", fetch = FetchType.LAZY)
    private Set<PartElementLibrary> partsElement;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id",  nullable = false)
    private EquipmentLibrary equipmentLibrary;
}