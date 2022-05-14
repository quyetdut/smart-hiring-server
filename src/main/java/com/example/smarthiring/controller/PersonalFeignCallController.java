package com.example.smarthiring.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdev.iresource.personal.common.vo.ChartSkill;
import com.smartdev.iresource.personal.dto.RadaChartDTO;
import com.smartdev.iresource.personal.entity.Capabilities;
import com.smartdev.iresource.personal.entity.Position;
import com.smartdev.iresource.personal.entity.ProfileCapabilities;
import com.smartdev.iresource.personal.entity.Profiles;
import com.smartdev.iresource.personal.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.exceptions.MicroserviceException;
import com.smartdev.iresource.personal.repository.*;
import com.smartdev.iresource.personal.services.MicroServiceService;
import com.smartdev.iresource.personal.services.ProjectMatchingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
/*@CrossOrigin(origins = "*", maxAge = 3600)*/
@Slf4j
@RequestMapping("/feign-client")
public class FeignCallController {

    private final MicroServiceService microServiceService;
    private final ResponseEntity<String> OkResponse = ResponseEntity.ok("ok");
    private final ResponseEntity<String> failedResponse = ResponseEntity.badRequest().body("Failed!");

    private final ProfilePositionRepository profilePositionRepository;
    private final ProfileRepository profileRepository;
    private final CapabilitiesRepository capabilitiesRepository;
    private final ProfileCapabilitiesRepository profileCapabilitiesRepository;
    private final PositionRepository positionRepository;
    private final PositionCapabilitiesRepository positionCapabilitiesRepository;
    private final ProjectMatchingService projectMatchingService;

    @GetMapping("/check-techID")
    public ResponseEntity<String> checkTechID(@RequestParam(value = "id") Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        Capabilities capability = microServiceService.checkTechID(id);
        try {
            if (capability != null) {
                return ResponseEntity.ok(objectMapper.writeValueAsString(capability));
            } else {
                return ResponseEntity.badRequest().body(ExceptionDefinition.CAPABILITY_NOT_FOUND.getMessage());
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getCapaName/{id}")
    public String getCapaName(@PathVariable Integer id) {
        Capabilities capabilities = capabilitiesRepository.getById(id);

        return capabilities.getName();
    }

    @GetMapping("/getToRadarChart")
    public RadaChartDTO getToRadarChart(@RequestParam(value = "userId") Integer userId,
                                        @RequestParam(value = "positionId") Integer positionId) {
        try {
            RadaChartDTO radaChartDTOS = new RadaChartDTO();
            radaChartDTOS.setUserId(userId);

            Position userPosition = positionRepository.getById(positionId);
            radaChartDTOS.setIcon(userPosition.getIcon());
            radaChartDTOS.setPositionName(userPosition.getName());

            Profiles profile = profileRepository.findByUserId(userId).get();
            radaChartDTOS.setName(profile.getFirstName() + " " + profile.getLastName());

            radaChartDTOS.setMatch(80);

            List<ChartSkill> capabilities = new ArrayList<>();

            List<ProfileCapabilities> profileCapabilities =
                    profileCapabilitiesRepository.findByProfileId(profile.getId());

            for (ProfileCapabilities pc : profileCapabilities) {
                Capabilities capabilities1 = capabilitiesRepository.findById(pc.getCapabilitiesId()).get();

                if (positionCapabilitiesRepository.findByPositionIdAndCapabilitiesId(positionId, pc.getCapabilitiesId()).isPresent()) {
                    ChartSkill chartSkill = new ChartSkill();
                    chartSkill.setName(capabilities1.getName());
                    chartSkill.setLevel(pc.getLevel());
                    capabilities.add(chartSkill);
                }
            }

            radaChartDTOS.setCapabilities(capabilities);
            return radaChartDTOS;
        } catch (MicroserviceException e) {
            return null;
        }
    }

    @GetMapping("/is-user-have-position-by-id")
    public ResponseEntity<String> isUserHavePositionById(Integer userId ,Integer positionId) {
        try {
            Profiles profiles = profileRepository.findByUserId(userId).get();


        Boolean existByUserIdAndPositionId = profilePositionRepository
                .existsByProfileIdAndPositionId(profiles.getId(), positionId);

        if (existByUserIdAndPositionId) return OkResponse;
        } catch (Exception e) {
            return failedResponse;
        }
        return failedResponse;
    }

    @GetMapping("/get-count-matching")
    public Integer getCountMatching(@RequestParam(value = "projectId") Integer projectId, @RequestParam(value = "positionId") Integer positionId) {
        return projectMatchingService.getCountMatching(projectId, positionId);
    }
}
