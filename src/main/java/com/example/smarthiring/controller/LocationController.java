package com.example.smarthiring.controllers;


import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.LocationRequestDto;
import com.smartdev.iresource.personal.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("location")
/*@CrossOrigin(origins = "*",maxAge = 3600)*/
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
