package com.example.smarthiring.service;

import com.example.smarthiring.dto.ProjectPersonasDTO;

import java.util.List;

public interface ProjectPersonasService {
    Boolean updateNumberCurrentProjectPersonas(ProjectPersonasDTO personasDTO);

    ProjectPersonasDTO updateProjectPersonasWithProjectId(ProjectPersonasDTO projectPersonasDTO, Integer projectId);

    List<Integer> getCapabilitiesId(Integer projectId, Integer positionId);
}
