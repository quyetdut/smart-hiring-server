package com.example.smarthiring.controllers;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.CapabilityLevelDto;
import com.smartdev.iresource.personal.services.ProfileCapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile-capability")
public class ProfileCapabilitiesController {

    @Autowired
    private ProfileCapabilitiesService profileCapabilitiesService;

    @PostMapping("/create-and-update/{profileId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("profileId") Integer profileId, @RequestBody List<CapabilityLevelDto> capabilitiesLevel){
        if (profileCapabilitiesService.createAndUpdate(profileId, capabilitiesLevel)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
