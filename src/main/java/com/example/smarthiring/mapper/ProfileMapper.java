package com.example.smarthiring.mapper;


import com.example.smarthiring.dto.CapabilityLevelDto;
import com.example.smarthiring.dto.CertificationDto;
import com.example.smarthiring.dto.ProfileResponseDto;
import com.example.smarthiring.dto.WorkExperienceDto;
import com.example.smarthiring.entity.Division;
import com.example.smarthiring.entity.Locations;
import com.example.smarthiring.entity.Profiles;
import com.example.smarthiring.entity.WorkExperiences;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class ProfileMapper {

    private ProfileMapper(){}
    public static ProfileResponseDto toProfileDtoResponse (Profiles profiles, Locations locations, List<CapabilityLevelDto> capabilities, Set<String> positions, List<WorkExperiences> workExperiences, List<CertificationDto> certifications, Division division){
        ProfileResponseDto profileDtoResponse = new ProfileResponseDto();
        List<WorkExperienceDto> workExperienceDtos = new ArrayList<>();
        profileDtoResponse.setProfileId(profiles.getId());
        profileDtoResponse.setUserId( profiles.getUserId());
        profileDtoResponse.setFirstName( profiles.getFirstName());
        profileDtoResponse.setLastName( profiles.getLastName());
        profileDtoResponse.setDivision( division.getName());
        profileDtoResponse.setPosition(profiles.getPosition());
        profileDtoResponse.setContractualTerm( profiles.getContractualTerm());
        profileDtoResponse.setLocation( locations.getCity());
        for(WorkExperiences workExperiences1 : workExperiences){
            WorkExperienceDto workExperienceDto = new WorkExperienceDto();
            workExperienceDto.setId(workExperiences1.getId());
            workExperienceDto.setEmployer(workExperiences1.getEmployer());
            workExperienceDto.setBusinessType(workExperiences1.getBusinessType());
            workExperienceDto.setDescription(workExperiences1.getDescription());
            workExperienceDto.setEndAt(workExperiences1.getEndAt());
            workExperienceDto.setPosition(workExperiences1.getPosition());
            workExperienceDto.setStartAt(workExperiences1.getStartAt());
            workExperienceDtos.add(workExperienceDto);
        }
        profileDtoResponse.setWorkExperiences(workExperienceDtos);
        profileDtoResponse.setCapabilities(capabilities);
        profileDtoResponse.setCertifications(certifications);
        return profileDtoResponse;
    }
}
