package com.example.smarthiring.controllers;

import com.smartdev.iresource.personal.common.enums.InterestingStatus;
import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.PositionUserDetailDto;
import com.smartdev.iresource.personal.dto.ProfileDto;
import com.smartdev.iresource.personal.dto.ProfileResponseDto;
import com.smartdev.iresource.personal.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/profiles")
//@CrossOrigin(origins = "*",maxAge = 3600)
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping("/create-and-update")
    public ResponseEntity<Object>  createProfile(@RequestBody ProfileDto profileDto){
        if (profileService.createProfile(profileDto)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/is-exist/{userId}")
    public Boolean isExistProfile(@PathVariable Integer userId) {
        return profileService.isExistProfile(userId);
    }

    @GetMapping ("/{userId}")
    public  ResponseEntity<Object> getProfileByUserId(@PathVariable Integer userId){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(profileService.getProfileByUserId(userId), ResponseResult.SUCCESS));
    }

    @GetMapping("/ProductOwner/{userId}")
    public ResponseEntity<Object> createProfilePO(@PathVariable Integer userId){
        return ResponseEntity.ok(ResponseHandler.getInstance().response(profileService.getProfilePO(userId), ResponseResult.SUCCESS));
    }

    @GetMapping("/get-username-by-id")
    public String getUsernameByUserId(@RequestParam(value = "userId") Integer userId) {
        return profileService.getUsernameByUserId(userId);
    }

    @GetMapping("/get-persona-users-detail")
    public ResponseEntity<Object> getPersonaUsersDetail(@RequestParam(value = "projectId") Integer projectId,
                                                        @RequestParam(value = "positionId") Integer positionId,
                                                        @RequestParam(value = "interestingStatus", required = false) String status) {
        InterestingStatus interestingStatus;
        try {
            interestingStatus = InterestingStatus.valueOf(status);
        } catch (Exception ex) {
            interestingStatus = null;
        }

        Map<String, List<PositionUserDetailDto>> result = new HashMap<>();
        result.put("member", profileService.getPositionUsersDetail(projectId, positionId, interestingStatus));
        return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
    }
    @GetMapping("/get-all-employees")
    public ResponseEntity<Object> getAll() {
        Optional<List<ProfileResponseDto>> responseDTOs = profileService.getAll();
        if (responseDTOs.isPresent()) return ResponseEntity.ok(ResponseHandler.getInstance().response(responseDTOs.get(), ResponseResult.SUCCESS));
        return ResponseEntity.internalServerError().build();
    }
}
