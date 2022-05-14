package com.example.smarthiring.service.impl;

import com.smartdev.iresource.project.entity.ProjectProcess;
import com.smartdev.iresource.project.repository.ProcessRepository;
import com.smartdev.iresource.project.repository.ProjectProcessRepository;
import com.smartdev.iresource.project.service.ProjectProcessService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProjectProcessServiceImpl implements ProjectProcessService {
    private final ProjectProcessRepository projectProcessRepository;
    private final ProcessRepository processRepository;

    @Override
    public Set<Process> getProcessByProjectId(Integer ProjectId) {
        Set<Process> processes = new HashSet<>();

        List<ProjectProcess> projectProcessList = projectProcessRepository.findAllByProjectId(ProjectId);
        for (ProjectProcess projectProcess : projectProcessList) {
            processes.add(processRepository.getById(projectProcess.getProcessId()));
        }
        return processes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createAndUpdate(Integer projectId, List<Integer> processIds) {
        projectProcessRepository.deleteAllByProjectId(projectId);
        List<ProjectProcess> projectProcesses = new ArrayList<>();
        processIds.forEach(processId -> {
            ProjectProcess projectProcess = new ProjectProcess();
            projectProcess.setProjectId(projectId);
            projectProcess.setProcessId(processId);
            projectProcesses.add(projectProcess);
        });

        projectProcessRepository.saveAll(projectProcesses);
        return true;
    }
}
