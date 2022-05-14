package com.smartdev.iresource.project.converter;

import com.smartdev.iresource.project.common.vo.Capabilities;
import com.smartdev.iresource.project.common.vo.Position;
import com.smartdev.iresource.project.dto.PersonasTechnicalDTO;
import com.smartdev.iresource.project.dto.ProjectPersonasDTO;
import com.smartdev.iresource.project.entity.PersonasTechnical;
import com.smartdev.iresource.project.entity.ProjectPersonas;
import com.smartdev.iresource.project.service.feignclient.service.PersonaFeignClientService;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
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
        dto.setPositionImg(position.getImgPath());
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
