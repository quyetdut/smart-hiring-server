package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.InterestingStatus;
import com.smartdev.iresource.personal.common.feignclient.ProjectFeignClient;
import com.smartdev.iresource.personal.dto.ProjectUserStatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class ProjectFeignClientServiceImpl {
    private final ProjectFeignClient projectFeignClient;

    public Map<Integer, Object> getProjectsByIdList(List<Integer> ids) {
        try {
            return projectFeignClient.getProjectsByIdList(ids);
        } catch (Exception e) {
            log.error("getProjectsByIdList method from ProjectCreationService");
            return null;
        }
    }

    public Object getProjectSkills(Integer id) {
        try {
            return projectFeignClient.getProjectSkills(id);
        } catch (Exception e) {
            log.error("getProject method from ProjectCreationService");
            return null;
        }
    }

    public List<ProjectUserStatusDto> getProjectMembers(Integer projectId) {
        return projectFeignClient.getProjectMembers(projectId).getBody();
    }

    public List<Integer> getUsersInterestStatus(Integer projectId, InterestingStatus interestingStatus) {
        return projectFeignClient.getUsersInterestStatus(projectId, interestingStatus);
    }

    public List<Integer> getProjectPersonaCapabilities(Integer projectId, Integer positionId) {
        return projectFeignClient.getProjectPersonaCapabilities(projectId, positionId);
    }
}
