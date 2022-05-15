package com.example.smarthiring.converter;

import com.example.smarthiring.dto.PersonasTechnicalDTO;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.entity.Position;
import com.example.smarthiring.entity.ProjectPersonas;

import java.util.Set;

public class ProjectPersonasConverter {

    public static ProjectPersonas toEntity(ProjectPersonasDTO dto) {
        ProjectPersonas projectPersonas = new ProjectPersonas();

        projectPersonas.setId(dto.getId());
        projectPersonas.setNumberCurrent(dto.getNumberCurrent());
        projectPersonas.setNumberNeeded(dto.getNumberNeeded());
        projectPersonas.setMonthNeeded(dto.getMonthNeeded());
        projectPersonas.setUtilization(dto.getUtilization());
        projectPersonas.setProjectId(dto.getProjectId());
        projectPersonas.setPositionId(dto.getPositionId());

        return projectPersonas;
    }

    public static ProjectPersonasDTO toDTO(
            ProjectPersonas projectPersonasEntity,
            Position position,
            Set<PersonasTechnicalDTO>  personasTechnicalEntity
    ) {
        ProjectPersonasDTO dto = new ProjectPersonasDTO();


        dto.setId(projectPersonasEntity.getId());
        dto.setPositionName(position.getName());
//        dto.setPositionImg(position.getImgPath());
        dto.setNumberCurrent(projectPersonasEntity.getNumberCurrent());
        dto.setNumberNeeded(projectPersonasEntity.getNumberNeeded());
        dto.setMonthNeeded(projectPersonasEntity.getMonthNeeded());
        dto.setUtilization(projectPersonasEntity.getUtilization());
        dto.setProjectId(projectPersonasEntity.getProjectId());
        dto.setPositionId(projectPersonasEntity.getPositionId());

        dto.setPersonasTechnicals(personasTechnicalEntity);

        return dto;
    }


}
