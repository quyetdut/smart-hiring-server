package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.PageProjectMatchingDto;
import com.smartdev.iresource.personal.dto.ProjectMatchingDto;
import com.smartdev.iresource.personal.dto.ProjectMatchingResponseDto;

public interface ProjectMatchingService {
    boolean saveMatchingScore(ProjectMatchingDto projectMatchingDto);

    Boolean deleteProjectMatching(Integer userId, Integer projectId);

    PageProjectMatchingDto getProjectMatchingForUser(Integer userId, String filterValue, Integer page, Integer size);

    ProjectMatchingResponseDto getMatchingScore(Integer userId, Integer projectId);

    Integer getCountMatching(Integer projectId, Integer positionId);
}
