package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.CapabilityLevelDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.ProfileCapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persona/profile-capability")
@CrossOrigin(origins = "*", maxAge = 3600)
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
