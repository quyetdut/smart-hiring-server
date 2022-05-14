package com.smartdev.iresource.project.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.smartdev.iresource.project.common.enums.ResponseResult;
import com.smartdev.iresource.project.common.response.ResponseHandler;
import com.smartdev.iresource.project.dto.ProjectCreationDTO;
import com.smartdev.iresource.project.dto.ProjectInfoDTO;
import com.smartdev.iresource.project.dto.ProjectPersonasDTO;
import com.smartdev.iresource.project.entity.Process;
import com.smartdev.iresource.project.entity.ProjectMember;
import com.smartdev.iresource.project.entity.Tool;
import com.smartdev.iresource.project.exception.FileStorageException;
import com.smartdev.iresource.project.service.ProjectCreationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

@RestController
@RequestMapping("/projects/creation/{poId}")
@RequiredArgsConstructor
@Slf4j
public class CreateProjectController {

    private final ProjectCreationService projectCreationService;

    @PostMapping()
    public ResponseEntity<Object> createProject(
            @PathVariable(value = "poId") Integer poId,
            @RequestParam(value = "projectInfo") String JSON_projectInfo,
            @RequestParam(value = "tools") String JSON_tools,
            @RequestParam(value = "processes") String JSON_processes,
            @RequestParam(value = "members", required = false) String JSON_members,
            @RequestParam(value = "projectPersonas") String JSON_projectPersonas,
            @RequestParam(value = "projectDocuments", required = false) MultipartFile[] projectDocuments,
            @RequestParam(value = "image", required = false) MultipartFile projectImage
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ProjectInfoDTO projectInfo = objectMapper.readValue(
                    JSON_projectInfo, ProjectInfoDTO.class);
            Set<Tool> projectTools = objectMapper.readValue(JSON_tools, new TypeReference<Set<Tool>>(){});
            Set<ProjectPersonasDTO> projectPersonas = objectMapper.readValue(
                    JSON_projectPersonas, new TypeReference<Set<ProjectPersonasDTO>>(){});
            Set<ProjectMember> projectMembers = objectMapper.readValue(
                    JSON_members, new TypeReference<Set<ProjectMember>>(){});
            Set<Process> projectProcesses = objectMapper.readValue(
                    JSON_processes, new TypeReference<Set<Process>>(){});

            ProjectCreationDTO projectCreationDTOParse = new ProjectCreationDTO();
            projectCreationDTOParse.setProjectInfo(projectInfo);
            projectCreationDTOParse.setTools(projectTools);
            projectCreationDTOParse.setMembers(projectMembers);
            projectCreationDTOParse.setProcesses(projectProcesses);
            projectCreationDTOParse.setProjectPersonas(projectPersonas);

            if (projectCreationService.createProject(poId, projectCreationDTOParse, projectImage, projectDocuments)) {
                return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
            } else {
                return ResponseEntity.internalServerError().build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Create project failed: {} {}", ex.getClass(), ex.getMessage());
            throw new FileStorageException("error: " + ex.getClass());
        }
    }
}
