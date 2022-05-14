package com.example.smarthiring.converter;

import com.example.smarthiring.dto.PersonasTechnicalDTO;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.entity.PersonasTechnical;
import com.example.smarthiring.entity.Position;
import com.example.smarthiring.entity.ProjectPersonas;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Slf4j
public class PersonaConverter {

    public static ProjectPersonasDTO toPersonaDTO(
            ProjectPersonas projectPersonas,
            Position position,
            Set<PersonasTechnicalDTO> personasTechnical
    ) {
        ProjectPersonasDTO personasDTO = new ProjectPersonasDTO();

        personasDTO.setId(projectPersonas.getId());
        personasDTO.setPositionId(projectPersonas.getPositionId());
        personasDTO.setPositionName(position.getName());
        personasDTO.setPositionImg(position.getImgPath());
        personasDTO.setNumberNeeded(projectPersonas.getNumberNeeded());
        personasDTO.setNumberCurrent(projectPersonas.getNumberCurrent());
        personasDTO.setMonthNeeded(projectPersonas.getMonthNeeded());
        personasDTO.setUtilization(projectPersonas.getUtilization());
        personasDTO.setProjectId(projectPersonas.getProjectId());
        personasDTO.setPersonasTechnicals(personasTechnical);

        return personasDTO;
    }

    public static Map<String, Object> toEntity(ProjectPersonasDTO personasDTO) {
        Map<String, Object> result = new HashMap<>();

        result.put("projectPersonas", new ProjectPersonas(
                personasDTO.getId(),
                personasDTO.getNumberNeeded(),
                personasDTO.getNumberCurrent(),
                personasDTO.getMonthNeeded(),
                personasDTO.getUtilization(),
                personasDTO.getPositionId()
        ));

        Set<PersonasTechnical> technicals = new HashSet<>();

        personasDTO.getPersonasTechnicals().forEach(personasTechnicalDTO -> {
            technicals.add(PersonasTechnicalConvertor.toEntity(personasTechnicalDTO));
        });

        result.put("technicals", technicals);

        return result;
    }
}
