package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponseDto {

    private Integer profileId;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String contractualTerm;
    private String division;
    private String location;
    private String position;
    private List<CapabilityLevelDto> capabilities;
    private List<WorkExperienceDto> workExperiences;
    private List<CertificationDto> certifications;

}
