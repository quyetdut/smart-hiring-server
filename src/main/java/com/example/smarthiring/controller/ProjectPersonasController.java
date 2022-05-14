package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProjectPersonasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project-personas/")
@RequiredArgsConstructor
@Slf4j
public class ProjectPersonasController {

    private final ProjectPersonasService projectPersonasService;

    @PutMapping("/numberCurrent")
    public ResponseEntity<Object> updateNumberCurrentProjectPersonas(@RequestBody ProjectPersonasDTO personasDTO) {
        if (projectPersonasService.updateNumberCurrentProjectPersonas(personasDTO)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/update/{projectId}")
    public ResponseEntity<Object> updateProjectPersona(
            @RequestBody ProjectPersonasDTO projectPersonasDTO,
            @PathVariable(value = "projectId") Integer projectId
    ) {

        ProjectPersonasDTO projectPersonasResponse =
                projectPersonasService.updateProjectPersonasWithProjectId(
                        projectPersonasDTO,
                        projectId
                );

        if (projectPersonasResponse != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(projectPersonasResponse, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
