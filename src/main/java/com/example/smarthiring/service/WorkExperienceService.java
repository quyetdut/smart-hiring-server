package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.WorkExperienceDto;

public interface WorkExperienceService {
    WorkExperienceDto createAndUpdate(WorkExperienceDto workExperienceRequestDto , Integer profileId);
}
