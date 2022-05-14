package com.example.smarthiring.service;

import com.example.smarthiring.dto.ProjectCreationDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectCreationService {

    Boolean createProject(Integer poId, ProjectCreationDTO projectDTO, MultipartFile image, MultipartFile[] documents);
}
