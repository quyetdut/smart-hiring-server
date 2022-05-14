package com.example.smarthiring.service;

import com.example.smarthiring.dto.WorkExperienceDto;

public interface WorkExperienceService {
    WorkExperienceDto createAndUpdate(WorkExperienceDto workExperienceRequestDto , Integer profileId);
}
