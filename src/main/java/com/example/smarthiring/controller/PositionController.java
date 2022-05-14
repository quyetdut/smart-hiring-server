package com.example.smarthiring.controllers;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.PositionRequestDto;
import com.smartdev.iresource.personal.entity.Position;
import com.smartdev.iresource.personal.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/position")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class PositionController {

    @Autowired
    private PositionService positionService;

    @PostMapping("")
    public ResponseEntity<Object>  createPosition (@RequestBody PositionRequestDto positionRequestDto){
        if (positionService.createPosition(positionRequestDto)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping()
    public ResponseEntity<Object>  getPosition(){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(positionService.getListPostion(), ResponseResult.SUCCESS));
    }

    @GetMapping("/role/{name}")
    public ResponseEntity<Object>  getPositionByRole(@PathVariable String name){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(positionService.getListPositionByRole(name), ResponseResult.SUCCESS));
    }

    @GetMapping("/{id}")
    public Position getPositionById(@PathVariable Integer id) {
        return positionService.getPositionById(id);
    }

    @GetMapping("/project-service/{id}")
    public Boolean isExistPositionById(@PathVariable Integer id){
        return positionService.getPositionByIdForProjectservice(id);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<Object> getPositionByProfileId(@PathVariable Integer userId){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(positionService.getPositionByProfileId(userId), ResponseResult.SUCCESS));
    }
}