package com.smartdev.iresource.project.controller;

import com.smartdev.iresource.project.common.enums.ResponseResult;
import com.smartdev.iresource.project.common.response.ResponseHandler;
import com.smartdev.iresource.project.dto.ProjectPersonasDTO;
import com.smartdev.iresource.project.service.ProjectPersonasService;
import com.smartdev.iresource.project.service.ProjectProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project-process")
@RequiredArgsConstructor
@Slf4j
public class ProjectProcessController {
    private final ProjectProcessService projectProcessService;

    @PostMapping("/createAndUpdate/{projectId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("projectId") Integer projectId, @RequestParam("processIds") List<Integer> processIds) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(projectProcessService.createAndUpdate(projectId, processIds), ResponseResult.SUCCESS));
    }
}
