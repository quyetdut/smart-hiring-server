package com.example.smarthiring.converter;

import com.example.smarthiring.dto.PersonasTechnicalDTO;
import com.example.smarthiring.entity.Capabilities;
import com.example.smarthiring.entity.PersonasTechnical;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class PersonasTechnicalConvertor {

    public static PersonasTechnicalDTO toDTO(PersonasTechnical personasTechnical, Capabilities capabilities) {
        PersonasTechnicalDTO technicalDTO = new PersonasTechnicalDTO();

        technicalDTO.setCapabilitiesId(personasTechnical.getCapabilitiesId());
        technicalDTO.setLevel(personasTechnical.getLevel());
        technicalDTO.setImportance(personasTechnical.getImportance());

        if (capabilities != null) {
            technicalDTO.setCapabilityName(capabilities.getName());
        }

        return technicalDTO;
    }

    public static PersonasTechnical toEntity(PersonasTechnicalDTO personasTechnicalDTO) {
        return new PersonasTechnical(
                personasTechnicalDTO.getCapabilitiesId(),
                personasTechnicalDTO.getLevel(),
                personasTechnicalDTO.getImportance()
        );
    }
}
