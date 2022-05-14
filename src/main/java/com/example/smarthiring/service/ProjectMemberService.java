package com.example.smarthiring.service;

import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.entity.ProjectMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectMemberService {
    ProjectMember addProjectMember(ProjectMember projectMember);

    void deleteProjectMember(Integer projectId, Integer userId);

    ResponseEntity<String> setforlong(Integer projectId, Integer userId);

    List<ProjectUserStatusDTO> getProjectMembers(Integer projectId);
}
