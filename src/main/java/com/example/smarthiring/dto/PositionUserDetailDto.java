package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PositionUserDetailDto {
    @JsonProperty("capabilities")
    List<CapabilityLevelDto> capabilityLevelDto;
    Integer userId;
    String icon;
    Integer matching;
    String name;
    String positionName;
}
