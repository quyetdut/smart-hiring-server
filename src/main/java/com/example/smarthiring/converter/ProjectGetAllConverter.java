package com.example.smarthiring.converter;

import com.example.smarthiring.dto.ProjectInfoDTO;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.dto.ProjectResponseDTO;
import com.example.smarthiring.entity.Document;
import com.example.smarthiring.entity.*;

import com.example.smarthiring.entity.Process;
import lombok.extern.slf4j.Slf4j;

import java.time.ZonedDateTime;
import java.util.Set;

@Slf4j
public class ProjectGetAllConverter {
    public static ProjectResponseDTO toProjectCreationDTO(
            Project project,
            Set<Tool> tools,
            Set<Process> processes,
            Set<ProjectPersonasDTO> projectPersonas,
            Set<ProjectMember> projectMembers,
            Set<Document> documents) {

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(project.getId());
        projectResponseDTO.setImgPath(project.getImgPath());
        projectResponseDTO.setProjectName(project.getProjectName());
        projectResponseDTO.setProjectStatus(project.getProjectStatus());
        projectResponseDTO.setProblemStatement(project.getProblemStatement());
        projectResponseDTO.setBigVision(project.getBigVision());
        projectResponseDTO.setValueProposition(project.getValueProposition());
        projectResponseDTO.setCustomer(project.getCustomer());
        projectResponseDTO.setRevenueStreams(project.getRevenueStreams());
        projectResponseDTO.setProjectCompletion(project.getProjectCompletion());
        projectResponseDTO.setStartAt(project.getStartAt().toLocalDate());
        projectResponseDTO.setEndAt(project.getEndAt().toLocalDate());
        projectResponseDTO.setTools(tools);
        projectResponseDTO.setProcesses(processes);
        projectResponseDTO.setProjectPersonas(projectPersonas);
        projectResponseDTO.setMembers(projectMembers);
        projectResponseDTO.setDocuments(documents);
        projectResponseDTO.setPoId(project.getPoId());
        return projectResponseDTO;
    }

    public static Project toEntity(ProjectInfoDTO projectInfoDTO) {

        Project project = new Project();
        project.setProjectName(projectInfoDTO.getProjectName());
        project.setProjectStatus(projectInfoDTO.getProjectStatus());
        project.setProblemStatement(projectInfoDTO.getProblemStatement());
        project.setBigVision(projectInfoDTO.getBigVision());
        project.setValueProposition(projectInfoDTO.getValueProposition());
        project.setCustomer(projectInfoDTO.getCustomer());
        project.setRevenueStreams(projectInfoDTO.getRevenueStreams());
        project.setProjectCompletion(projectInfoDTO.getProjectCompletion());
        project.setStartAt(ZonedDateTime.parse(projectInfoDTO.getStartAt()).toLocalDateTime());
        project.setEndAt(ZonedDateTime.parse(projectInfoDTO.getStartAt()).toLocalDateTime());
        project.setPoId(project.getPoId());
        return project;
    }
}
