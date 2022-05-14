package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.dto.ProjectUserStatusDTO;
import com.smartdev.iresource.project.entity.ProjectMember;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProjectMemberService {
    ProjectMember addProjectMember(ProjectMember projectMember);

    void deleteProjectMember(Integer projectId, Integer userId);

    ResponseEntity<String> setforlong(Integer projectId, Integer userId);

    List<ProjectUserStatusDTO> getProjectMembers(Integer projectId);
}
