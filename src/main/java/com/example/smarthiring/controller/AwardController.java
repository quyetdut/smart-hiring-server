package com.example.smarthiring.controller;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.AwardDto;
import com.smartdev.iresource.personal.entity.Awards;
import com.smartdev.iresource.personal.services.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/award")
/*@CrossOrigin(origins ="*", maxAge = 3600)*/
@RequiredArgsConstructor
public class AwardController {

    private final AwardService awardService;

    @PostMapping
    public ResponseEntity<Object>  createAward(@RequestBody AwardDto awardDto){
        if (awardService.createAward(awardDto)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }

    }

    @GetMapping()
    public ResponseEntity<Object>  findAwardById(@RequestParam(value = "awardId") Integer awardId){
        Awards award = awardService.findByAwardId(awardId);
        return ResponseEntity.ok(ResponseHandler.getInstance().response(award, ResponseResult.SUCCESS));
    }
    @GetMapping("/all")
    public ResponseEntity<Object>  getAllAward(){
        List<Awards> awards = awardService.getAllAward();
        return ResponseEntity.ok(ResponseHandler.getInstance().response(awards, ResponseResult.SUCCESS));
    }

    @PutMapping
    public ResponseEntity<Object>  updateAward(@RequestBody AwardDto awards, @RequestParam("id") Integer id){
        Awards award = awardService.updateAwardById(awards, id);
        return ResponseEntity.ok(ResponseHandler.getInstance().response(award, ResponseResult.SUCCESS));
    }
}
