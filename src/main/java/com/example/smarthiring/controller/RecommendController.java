package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.PageProjectMatchingDto;
import com.example.smarthiring.dto.ProjectMatchingDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.service.ProjectMatchingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/recommendation")
public class RecommendController {

    @Autowired
    private ProjectMatchingService projectMatchingService;

    @PostMapping("/set-project-matching-score")
    public ResponseEntity<Object> setMatchingScore(@RequestBody ProjectMatchingDto projectMatchingDto){
        log.info("projectMatchingDTO: {}", projectMatchingDto);
        if (projectMatchingService.saveMatchingScore(projectMatchingDto)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/explore-project")
    public ResponseEntity<Object> getMatchingProjects(@RequestParam("userId") Integer userId, @RequestParam(value = "filterValue", required = false) String filterValue, @RequestParam("page") Integer page, @RequestParam("size") Integer size) {
        PageProjectMatchingDto pageProjectMatchingDto = projectMatchingService.getProjectMatchingForUser(userId, filterValue, page, size);
        if (pageProjectMatchingDto != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(pageProjectMatchingDto, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        }
    }

    @DeleteMapping("/delete-project-matching")
    public ResponseEntity<Object> deleteProjectMatching(@RequestParam(value = "userId") Integer userId,
                                                        @RequestParam(value = "projectId") Integer projectId) {
        log.info("userId: {}", userId);
        Boolean isDeleted = projectMatchingService.deleteProjectMatching(userId, projectId);
        if (isDeleted) return ResponseEntity.ok(ResponseHandler.getInstance().response(true, ResponseResult.SUCCESS));
        throw new NotFoundException("can't delete project matching", 1024);
    }

    @GetMapping("/get-matching-score")
    public ResponseEntity<Object> showMatchingScore(@RequestParam(value = "userId") Integer userId,
                                                    @RequestParam(value = "projectId") Integer projectId) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(projectMatchingService.getMatchingScore(userId, projectId), ResponseResult.SUCCESS));
    }
}
