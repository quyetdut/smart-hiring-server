package com.example.smarthiring.controller;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.CertificationDto;
import com.smartdev.iresource.personal.services.CertifcationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certification")
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
