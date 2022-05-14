package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ProjectCollaboratorDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/persona/collaborators")
@RequiredArgsConstructor
public class PersonalCollaboratorController {

    @Autowired
    private ProfileService profileService;

    @GetMapping()
    public ResponseEntity<Object> getProjectCollaborators(@RequestParam("projectId") Integer projectId){
        List<ProjectCollaboratorDto> pcList = profileService.getProjectCollaborator(projectId);

        return ResponseEntity.ok(ResponseHandler.getInstance().response(pcList, ResponseResult.SUCCESS));
    }
}
