package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.exception.SomethingWrongException;
import com.example.smarthiring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth/message")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class MessageController {

    UserService userService;

    @GetMapping("/get-id-by-uid/{Uid}")
    public ResponseEntity<Object> getIdByUid(@PathVariable(value = "Uid") String Uid) {
        Integer userId = userService.getIdByUid(Uid);
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("userId", userId);
        if (userId != null) return ResponseEntity.ok(ResponseHandler.getInstance().response(responseData, ResponseResult.SUCCESS));
        throw new SomethingWrongException("get id false, not found by uid", 1345);
    }
}
