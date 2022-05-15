package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProjectProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/project-process")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProjectProcessController {
    private final ProjectProcessService projectProcessService;

    @PostMapping("/createAndUpdate/{projectId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("projectId") Integer projectId, @RequestParam("processIds") List<Integer> processIds) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(projectProcessService.createAndUpdate(projectId, processIds), ResponseResult.SUCCESS));
    }
}
