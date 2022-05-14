package com.example.smarthiring.dto;

import com.example.smarthiring.common.ProjectStatus;
import com.example.smarthiring.entity.*;
import com.example.smarthiring.entity.Process;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponseDTO implements Serializable {
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

    @NotBlank
    private Double projectCompletion;

    @NotBlank
    private LocalDate startAt;

    @NotBlank
    private LocalDate endAt;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Tool> tools;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Process> processes;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<ProjectPersonasDTO> projectPersonas;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<ProjectMember> members;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Document> documents;

    private Integer poId;
}
