package com.smartdev.iresource.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String contractualTerm;
    private String division;
    private String location;
    private String position;
    private HashMap<String,Integer> capabilities ;
    private HashMap<String, Integer> awards;
    private List<WorkExperienceDto> workExperiences;
    private Set<String> certifications;
}
