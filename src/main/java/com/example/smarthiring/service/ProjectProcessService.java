package com.example.smarthiring.service;

import java.util.List;
import java.util.Set;
import com.example.smarthiring.entity.Process;

public interface ProjectProcessService {
    Set<Process> getProcessByProjectId(Integer ProjectId);

    boolean createAndUpdate(Integer projectId, List<Integer> processIds);
}
