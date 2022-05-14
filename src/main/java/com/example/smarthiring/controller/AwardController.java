package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.AwardDto;
import com.example.smarthiring.entity.Awards;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.service.AwardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("persona/award")
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
