package com.example.smarthiring.service.impl;

import com.smartdev.iresource.project.entity.ProjectTool;
import com.smartdev.iresource.project.entity.Tool;
import com.smartdev.iresource.project.repository.ProjectToolRepository;
import com.smartdev.iresource.project.repository.ToolRepository;
import com.smartdev.iresource.project.service.ProjectToolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProjectToolServiceImpl implements ProjectToolService {

    private final ProjectToolRepository projectToolRepository;
    private final ToolRepository toolRepository;

    @Override
    public Set<Tool> getToolByProjectId(Integer projectId) {
        Set<Tool> tools = new HashSet<>();

        List<ProjectTool> projectToolList = projectToolRepository.findAllByProjectId(projectId);
        for (ProjectTool projectTool : projectToolList) {
            tools.add(toolRepository.getById(projectTool.getToolId()));
        }
        return tools;
    }
}
