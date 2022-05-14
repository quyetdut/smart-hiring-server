package com.example.smarthiring.controller;

import com.smartdev.iresource.authentication.common.enums.ResponseResult;
import com.smartdev.iresource.authentication.common.response.ResponseHandler;
import com.smartdev.iresource.authentication.exception.SomethingWrongException;
import com.smartdev.iresource.authentication.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/message")
@AllArgsConstructor
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
