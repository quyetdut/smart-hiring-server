package com.example.smarthiring.service;

import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.enums.InterestingStatus;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ServiceUtil {
    Boolean isTokenTruth(HttpServletRequest request);

    Boolean isExitPOId(Integer id);

    Boolean isExistUserById(Integer id);

    public List<Integer> getProjectPersonaCapabilities(Integer projectId, Integer positionId);

    List<ProjectUserStatusDTO> getProjectMembers(Integer projectId);
}
