package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.enums.InterestingStatus;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProjectUserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project/interesting")
@AllArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class InterestingController {
    private ProjectUserStatusService projectUserStatusService;

    @PostMapping("/interest")
    public ResponseEntity<Object> interest(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setInterestingStatus(InterestingStatus.INTERESTED);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/not-interest")
    public ResponseEntity<Object> notInterest(@RequestBody ProjectUserStatusDTO projectUserStatusDTO) {
        projectUserStatusDTO.setInterestingStatus(InterestingStatus.NOT_INTERESTED);
        if (projectUserStatusService.updateProjectUserStatus(projectUserStatusDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get-interest-status")
    public ResponseEntity<Object> getInterestingStatus(@RequestParam(value = "projectId") Integer projectId,
                                                       @RequestParam(value = "userId") Integer userId) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(projectUserStatusService.getInterestingStatus(projectId, userId), ResponseResult.SUCCESS));
    }
}
