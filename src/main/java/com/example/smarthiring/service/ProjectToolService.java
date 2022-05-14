package com.example.smarthiring.service;


import com.example.smarthiring.entity.Tool;

import java.util.Set;

public interface ProjectToolService {
    Set<Tool> getToolByProjectId(Integer projectId);
}
