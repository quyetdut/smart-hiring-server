package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.entity.Process;

import java.util.List;
import java.util.Set;

public interface ProjectProcessService {
    Set<Process> getProcessByProjectId(Integer ProjectId);

    boolean createAndUpdate(Integer projectId, List<Integer> processIds);
}
