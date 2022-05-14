package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.WorkExperienceDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.WorkExperienceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-experience")
public class WorkExperienceController {

    @Autowired
    private WorkExperienceService workExperienceService;

    @PostMapping("/create-and-update/{profileId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("profileId") Integer profileId, @RequestBody WorkExperienceDto workExperienceDto){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(workExperienceService.createAndUpdate(workExperienceDto, profileId), ResponseResult.SUCCESS));
    }
}
