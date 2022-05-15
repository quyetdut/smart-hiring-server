package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ProjectCollaboratorDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona/collaborators")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class PersonalCollaboratorController {

    @Autowired
    private ProfileService profileService;

    @GetMapping()
    public ResponseEntity<Object> getProjectCollaborators(@RequestParam("projectId") Integer projectId){
        List<ProjectCollaboratorDto> pcList = profileService.getProjectCollaborator(projectId);

        return ResponseEntity.ok(ResponseHandler.getInstance().response(pcList, ResponseResult.SUCCESS));
    }
}
