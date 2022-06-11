package com.example.smarthiring.service;

import com.example.smarthiring.dto.PageProjectMatchingDto;
import com.example.smarthiring.dto.ProjectMatchingDto;
import com.example.smarthiring.dto.ProjectMatchingResponseDto;
import com.example.smarthiring.entity.ProjectMatching;

import java.util.List;

public interface ProjectMatchingService {
//    boolean saveMatchingScore(ProjectMatchingDto projectMatchingDto);

//    Boolean deleteProjectMatching(Integer userId, Integer projectId);

    PageProjectMatchingDto getProjectMatchingForUser(Integer userId, String filterValue, Integer page, Integer size);

    List<ProjectMatching> getMatchingScore(Integer userId, Integer projectId);

    Integer getCountMatching(Integer projectId, Integer positionId);
}
