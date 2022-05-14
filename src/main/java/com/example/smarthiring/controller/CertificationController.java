package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.CertificationDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.CertifcationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/persona/certification")
public class CertificationController {
    @Autowired
    private CertifcationService certifcationService;

    @PostMapping("/create-and-update/{profileId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("profileId") Integer profileId, @RequestBody CertificationDto certificationDto){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(certifcationService.createAndUpdate(profileId, certificationDto), ResponseResult.SUCCESS));
    }

    @GetMapping("/delete/{certificationId}")
    public ResponseEntity<Object> createAndUpdate(@PathVariable("certificationId") Integer certificationId){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(certifcationService.deleteCertification(certificationId), ResponseResult.SUCCESS));
    }
}
