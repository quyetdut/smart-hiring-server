package com.example.smarthiring.controller;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.services.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/division")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*",maxAge = 3600)
public class DivisionController {

    private final DivisionService divisionService;
    @GetMapping()
    public ResponseEntity<Object> getAllDivision(){
        return  ResponseEntity.ok(ResponseHandler.getInstance().response(divisionService.getAllDivision(), ResponseResult.SUCCESS));
    }
}
