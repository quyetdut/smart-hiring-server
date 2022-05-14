package com.example.smarthiring.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonasTechnicalDTO {
    private Integer capabilitiesId;
    private String capabilityName;
    private Integer level;
    private Integer importance;
}
