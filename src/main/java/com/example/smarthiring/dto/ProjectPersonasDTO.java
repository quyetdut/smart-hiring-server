package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPersonasDTO {
    private Integer id;
    private String positionName;
    private String positionImg;
    private Integer numberNeeded;
    private Integer numberCurrent;
    private Integer monthNeeded;
    private Double utilization;
    private Integer projectId;
    private Integer positionId;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Set<PersonasTechnicalDTO> personasTechnicals;
}
