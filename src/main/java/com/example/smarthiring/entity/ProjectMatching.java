package com.example.smarthiring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectMatching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "profileId not null")
    private Integer profileId;

    @NotNull(message = "projectId not null")
    private Integer projectId;

    @Column(name = "position_id")
    private Integer positionId;

    @NotNull(message = "matchingScore not null")
    private Integer matchingScore;

    @NotNull
    private String projectName;

    private String skills;
}
