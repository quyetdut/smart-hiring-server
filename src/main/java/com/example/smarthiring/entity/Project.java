package com.example.smarthiring.entity;

import com.example.smarthiring.enums.ProjectStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
public class Project implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String imgPath;

    @NotBlank
    private String projectName;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @NotBlank
    private String problemStatement;

    @NotBlank
    private String bigVision;

    @NotBlank
    private String valueProposition;

    @NotBlank
    private String customer;

    private String revenueStreams;

    @NotNull
    private Double projectCompletion;


    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    private Integer poId;

    public Project(
            String imgPath,
            String projectName,
            ProjectStatus projectStatus,
            String problemStatement,
            String bigVision,
            String valueProposition,
            String customer,
            String revenueStreams,
            Double projectCompletion,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        this.imgPath = imgPath;
        this.projectName = projectName;
        this.projectStatus = projectStatus;
        this.problemStatement = problemStatement;
        this.bigVision = bigVision;
        this.valueProposition = valueProposition;
        this.customer = customer;
        this.revenueStreams = revenueStreams;
        this.projectCompletion = projectCompletion;
        this.createdAt = LocalDateTime.now();
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
