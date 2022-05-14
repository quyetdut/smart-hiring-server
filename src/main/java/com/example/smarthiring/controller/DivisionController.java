package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.DivisionService;
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
