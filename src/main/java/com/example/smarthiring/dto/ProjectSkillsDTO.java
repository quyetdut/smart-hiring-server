package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectSkillsDTO {
    private Integer projectId;
    private String projectName;
    private Set<Integer> capabilitiesId;
}
