package com.example.smarthiring.controller;


import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.dto.UserPositionInteractReportDto;
import com.example.smarthiring.service.ProjectMemberService;
import com.example.smarthiring.service.ProjectUserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/project-members")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
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
