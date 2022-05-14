package com.example.smarthiring.controllers;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.WorkExperienceDto;
import com.smartdev.iresource.personal.services.WorkExperienceService;
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
