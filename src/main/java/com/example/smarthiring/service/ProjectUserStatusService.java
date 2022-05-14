package com.example.smarthiring.service;


import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.dto.UserPositionInteractReportDto;
import com.example.smarthiring.enums.InterestingStatus;

import java.util.List;

public interface ProjectUserStatusService {
    Boolean updateProjectUserStatus(ProjectUserStatusDTO projectUserStatusDTO);

    ProjectUserStatusDTO getCollaborateStatus(Integer projectId, Integer userId);

    ProjectUserStatusDTO getInterestingStatus(Integer projectId, Integer userId);

    Boolean isCollaborating(Integer projectId, Integer userId);

    UserPositionInteractReportDto getUserPositionInteractReport(Integer projectId, Integer positionId);

    List<Integer> getUsersByInterestStatus(Integer projectId, InterestingStatus status);
}
