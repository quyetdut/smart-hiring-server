package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.common.enums.InterestingStatus;
import com.smartdev.iresource.project.dto.UserPositionInteractReportDto;
import com.smartdev.iresource.project.dto.ProjectUserStatusDTO;

import java.util.List;

public interface ProjectUserStatusService {
    Boolean updateProjectUserStatus(ProjectUserStatusDTO projectUserStatusDTO);

    ProjectUserStatusDTO getCollaborateStatus(Integer projectId, Integer userId);

    ProjectUserStatusDTO getInterestingStatus(Integer projectId, Integer userId);

    Boolean isCollaborating(Integer projectId, Integer userId);

    UserPositionInteractReportDto getUserPositionInteractReport(Integer projectId, Integer positionId);

    List<Integer> getUsersByInterestStatus(Integer projectId, InterestingStatus status);
}
