package com.example.smarthiring.service;

import com.example.smarthiring.dto.PageProjectMatchingDto;
import com.example.smarthiring.dto.ProjectMatchingDto;
import com.example.smarthiring.dto.ProjectMatchingResponseDto;

public interface ProjectMatchingService {
    boolean saveMatchingScore(ProjectMatchingDto projectMatchingDto);

    Boolean deleteProjectMatching(Integer userId, Integer projectId);

    PageProjectMatchingDto getProjectMatchingForUser(Integer userId, String filterValue, Integer page, Integer size);

    ProjectMatchingResponseDto getMatchingScore(Integer userId, Integer projectId);

    Integer getCountMatching(Integer projectId, Integer positionId);
}
