package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.dto.ProjectCreationDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectCreationService {

    Boolean createProject(Integer poId, ProjectCreationDTO projectDTO, MultipartFile image, MultipartFile[] documents);
}
