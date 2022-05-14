package com.example.smarthiring.service;

import com.example.smarthiring.dto.ProjectInfoDTO;
import com.example.smarthiring.dto.ProjectResponseDTO;
import com.example.smarthiring.dto.ProjectSkillsDTO;
import com.example.smarthiring.dto.RadaChartDTO;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {
    Integer createProject(ProjectInfoDTO projectInfoDTO, Integer poId, MultipartFile imageFile);

    Map<String, Object> getAllProject(Integer poId, Integer page, String projectName);

    ProjectResponseDTO getProject(Integer projectId);

    Map<Integer, Object> getMapProjects(List<Integer> projectIds);

    ProjectSkillsDTO getProjectSkills(Integer projectId);

    List<ProjectResponseDTO> getCollaborateProjects(Integer userId, CollaborateStatus collaborateStatus);

    List<ProjectResponseDTO> getInterestedProjects(Integer userId, InterestingStatus interestingStatus);

    Optional<List<String>> getAllProjectNames();
}
