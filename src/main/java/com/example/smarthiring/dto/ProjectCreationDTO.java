package com.smartdev.iresource.project.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.smartdev.iresource.project.entity.Process;
import com.smartdev.iresource.project.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
