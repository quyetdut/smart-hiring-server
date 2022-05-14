package com.smartdev.iresource.project.controller;


import com.smartdev.iresource.project.dto.UserPositionInteractReportDto;
import com.smartdev.iresource.project.dto.ProjectUserStatusDTO;
import com.smartdev.iresource.project.service.ProjectMemberService;
import com.smartdev.iresource.project.service.ProjectUserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/project-members")
@AllArgsConstructor
@Slf4j
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;
    private ProjectUserStatusService projectUserStatusService;

    @GetMapping("")
    public ResponseEntity<List<ProjectUserStatusDTO>> getProjectMembers(@RequestParam("projectId") Integer projectId) {
       List<ProjectUserStatusDTO> result = projectMemberService.getProjectMembers(projectId);
       return ResponseEntity.ok(result);
    }

    @GetMapping("/get-user-position-interaction")
    public  ResponseEntity<UserPositionInteractReportDto> getUserPositionInteraction(@RequestParam(value = "projectId") Integer projectId,
                                                                                     @RequestParam(value = "positionId") Integer positionId) {
        return ResponseEntity.ok(projectUserStatusService.getUserPositionInteractReport(projectId, positionId));
    }
}
