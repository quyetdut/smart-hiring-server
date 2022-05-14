package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProjectUserStatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/collaborator")
@AllArgsConstructor
public class ProjectCollaboratorController {


    private ProjectUserStatusService projectUserStatusService;

    @GetMapping("/get-collaborate-status")
    public ResponseEntity<Object> getCollaborateStatus(@RequestParam(value = "projectId") Integer projectId,
                                                       @RequestParam(value = "userId") Integer userId) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(projectUserStatusService.getCollaborateStatus(projectId, userId), ResponseResult.SUCCESS));
    }

    @PostMapping("/user-collaborate")
    public ResponseEntity<Object> userCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.USER_REQUEST_COLLABORATE);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/po-collaborate")
    public ResponseEntity<Object> poCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.PROJECT_REQUEST_COLLABORATE);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/user-accept-collaborate")
    public ResponseEntity<Object> userAcceptCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.COLLABORATING);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/po-accept-collaborate")
    public ResponseEntity<Object> poAcceptCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.COLLABORATING);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/user-reject-collaborate")
    public ResponseEntity<Object> userRejectCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.USER_REJECT_COLLABORATE);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/po-reject-collaborate")
    public ResponseEntity<Object> poRejectCollaborate(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.PROJECT_REJECT_COLLABORATE);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/is-collaborating")
    public Boolean isCollaborating(@RequestParam(value = "projectId") Integer projectId,
                                   @RequestParam(value = "userId") Integer userId) {
        return projectUserStatusService.isCollaborating(projectId, userId);
    }
}
