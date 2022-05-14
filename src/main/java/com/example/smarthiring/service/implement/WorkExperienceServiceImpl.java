package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.WorkExperienceDto;
import com.example.smarthiring.entity.WorkExperiences;
import com.example.smarthiring.exception.FailException;
import com.example.smarthiring.repository.WorkExperienceRepository;
import com.example.smarthiring.service.WorkExperienceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WorkExperienceServiceImpl implements WorkExperienceService {

    @Autowired
    private WorkExperienceRepository workExperienceRepository;

    public WorkExperienceDto createAndUpdate(WorkExperienceDto workExperienceRequestDto , Integer profileId){

        WorkExperiences workExperiences;
        if (workExperienceRequestDto.getId() == null) {
            workExperiences = new WorkExperiences();
        } else {
            workExperiences = workExperienceRepository.findById(workExperienceRequestDto.getId()).get();
        }

        workExperiences.setEmployer(workExperienceRequestDto.getEmployer());
        workExperiences.setStartAt(workExperienceRequestDto.getStartAt());
        workExperiences.setEndAt(workExperienceRequestDto.getEndAt());
        workExperiences.setDescription(workExperienceRequestDto.getDescription());
        workExperiences.setBusinessType(workExperienceRequestDto.getBusinessType());
        workExperiences.setPosition(workExperienceRequestDto.getPosition());
        workExperiences.setProfileId(profileId);
        try {
            workExperiences = workExperienceRepository.save(workExperiences);
            workExperienceRequestDto.setId(workExperiences.getId());

            return workExperienceRequestDto;
        } catch ( Exception e){
            log.error(e.getMessage());
            throw new FailException("Create Work Experience Failed", 2033);
        }
    }
}
