package com.example.smarthiring.dto;

import com.example.smarthiring.entity.ProjectMember;
import com.example.smarthiring.entity.Tool;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.smarthiring.entity.Process;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreationDTO implements Serializable {
    @NotBlank
    private ProjectInfoDTO projectInfo;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Tool> tools;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<Process> processes;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<ProjectPersonasDTO> projectPersonas;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<ProjectMember> members;
}
