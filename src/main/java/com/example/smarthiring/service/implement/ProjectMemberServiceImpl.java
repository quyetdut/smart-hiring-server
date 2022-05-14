package com.example.smarthiring.service.impl;

import com.smartdev.iresource.project.common.ErrorLog;
import com.smartdev.iresource.project.common.ResponseMessage;
import com.smartdev.iresource.project.dto.ProjectUserStatusDTO;
import com.smartdev.iresource.project.entity.ProjectMember;
import com.smartdev.iresource.project.exception.SomethingWrongException;
import com.smartdev.iresource.project.repository.ProjectMemberRepository;
import com.smartdev.iresource.project.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectMemberServiceImpl implements ProjectMemberService, ErrorLog, ResponseMessage {

    private final ProjectMemberRepository projectMemberRepository;

    @Override
    public ProjectMember addProjectMember(ProjectMember projectMember) {
        try {
            return projectMemberRepository.save(projectMember);
        } catch (Exception ex) {
            throw new SomethingWrongException("Error during add project member!");
        }
    }

    @Override
    public void deleteProjectMember(Integer projectId, Integer userId) {
        try {
            projectMemberRepository.deleteByProjectIdAndUserId(projectId, userId);
        } catch (Exception ex) {
            System.out.println("aaa" + ex.getCause());
            throw new SomethingWrongException("Error during delete project member!");
        }
    }

    @Override
    public ResponseEntity<String> setforlong(Integer projectId, Integer userId) {
        return ResponseEntity.ok("done");
    }

    @Override
    public List<ProjectUserStatusDTO> getProjectMembers(Integer projectId) {
        List<ProjectUserStatusDTO> result = new ArrayList<>();

        Set<ProjectMember> projectMembers =  projectMemberRepository.findAllByProjectId(projectId);
        projectMembers.forEach(pm -> {
            ProjectUserStatusDTO pus = new ProjectUserStatusDTO();
            pus.setProjectId(projectId);
            pus.setUserId(pm.getUserId());
            pus.setPositionId(pm.getPositionId());
            result.add(pus);
        });

        return result;
    }
}
