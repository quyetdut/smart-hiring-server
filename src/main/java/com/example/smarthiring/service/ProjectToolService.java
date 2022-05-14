package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.entity.Tool;

import java.util.Set;

public interface ProjectToolService {
    Set<Tool> getToolByProjectId(Integer projectId);
}
