package com.smartdev.iresource.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonasTechnical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "project_personas_id")
    private Integer projectPersonasId;

    private Integer capabilitiesId;
    private Integer level;
    private Integer importance;

    public PersonasTechnical(Integer capabilitiesId, Integer level, Integer importance) {
        this.capabilitiesId = capabilitiesId;
        this.level = level;
        this.importance = importance;
    }
}
