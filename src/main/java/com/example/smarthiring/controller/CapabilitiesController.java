package com.example.smarthiring.controller;

import com.smartdev.iresource.personal.common.enums.ResponseResult;
import com.smartdev.iresource.personal.common.response.ResponseHandler;
import com.smartdev.iresource.personal.dto.CapabilityDto;
import com.smartdev.iresource.personal.dto.CapabilityRequestDto;
import com.smartdev.iresource.personal.entity.Capabilities;
import com.smartdev.iresource.personal.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.services.CapabilytiesService;
import com.smartdev.iresource.personal.services.PositionCapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/capability")
/*@CrossOrigin(origins = "*",maxAge = 3600)*/
public class CapabilitiesController {

    @Autowired
    private CapabilytiesService capabilytiesService;

    @Autowired
    private PositionCapabilitiesService positionCapabilitiesService;

    @PostMapping()
    public ResponseEntity<Object>  createCapability(@RequestBody CapabilityRequestDto capabilityDtoRequest){
        boolean isCreated = positionCapabilitiesService.createPositionCapabilities(capabilityDtoRequest);
        if (isCreated) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            throw new FailException(ExceptionDefinition.CREATE_CAPABILITY_FAIL.getMessage(), ExceptionDefinition.CREATE_CAPABILITY_FAIL.getErrorCode());
        }
    }

    @GetMapping("/position")
    public ResponseEntity<Object>  getListCapabilityByPositionId(@RequestParam(value = "positionId", required = true) Integer positionId){
        List<CapabilityDto> capabilityDtoList = capabilytiesService.getListCapabilityByPositionId(positionId);

        return ResponseEntity.ok(ResponseHandler.getInstance().response(capabilityDtoList, ResponseResult.SUCCESS));
    }
    @GetMapping()
    public ResponseEntity<Object>  getListCapability(){
       return ResponseEntity.ok(ResponseHandler.getInstance().response(capabilytiesService.getListCapability(), ResponseResult.SUCCESS));
    }

    @GetMapping("/{id}")
    public Capabilities getCapabilityByID(@PathVariable Integer id) {
        return capabilytiesService.getCapabilityByid(id);
    }
}
