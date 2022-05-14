package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.common.enums.CollaborateStatus;
import com.smartdev.iresource.project.common.enums.InterestingStatus;
import com.smartdev.iresource.project.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {
    Integer createProject(ProjectInfoDTO projectInfoDTO, Integer poId, MultipartFile imageFile);

    Map<String, Object> getAllProject(Integer poId, Integer page, String projectName);

    Map<String, List<RadaChartDTO>> getProjectToRadarChart(Integer projectId, Integer positionId);

    ProjectResponseDTO getProject(Integer projectId);

    Map<Integer, Object> getMapProjects(List<Integer> projectIds);

    ProjectSkillsDTO getProjectSkills(Integer projectId);

    List<ProjectResponseDTO> getCollaborateProjects(Integer userId, CollaborateStatus collaborateStatus);

    List<ProjectResponseDTO> getInterestedProjects(Integer userId, InterestingStatus interestingStatus);

    Optional<List<String>> getAllProjectNames();
}
