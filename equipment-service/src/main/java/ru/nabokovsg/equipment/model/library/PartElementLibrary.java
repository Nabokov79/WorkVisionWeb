package ru.nabokovsg.equipment.model.library;

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
@Table(name = "parts_element_library")
public class PartElementLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "part_name")
    private String partElementName;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "element_id",  nullable = false)
    private ElementLibrary elementLibrary;
}