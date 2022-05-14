package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.dto.ProjectPersonasDTO;

import java.util.List;

public interface ProjectPersonasService {
    Boolean updateNumberCurrentProjectPersonas(ProjectPersonasDTO personasDTO);

    ProjectPersonasDTO updateProjectPersonasWithProjectId(ProjectPersonasDTO projectPersonasDTO, Integer projectId);

    List<Integer> getCapabilitiesId(Integer projectId, Integer positionId);
}
