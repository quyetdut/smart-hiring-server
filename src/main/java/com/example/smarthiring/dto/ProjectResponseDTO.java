package com.example.smarthiring.dto;

import com.example.smarthiring.entity.*;
import com.example.smarthiring.entity.Process;
import com.example.smarthiring.enums.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startAt;

    @NotBlank
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
