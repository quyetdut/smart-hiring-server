package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.LocationRequestDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persona/location")
@CrossOrigin(origins = "*",maxAge = 3600)
public class LocationController {

    @Autowired
    private LocationService locationService;

    @PostMapping
    public ResponseEntity<Object>  createLocation(@RequestBody LocationRequestDto locationRequestDto){
        if (locationService.createLocation(locationRequestDto)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public ResponseEntity<Object>  getListLocation(){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(locationService.getAllLocation(), ResponseResult.SUCCESS));
    }

}
