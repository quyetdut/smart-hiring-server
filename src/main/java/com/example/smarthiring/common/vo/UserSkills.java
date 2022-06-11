package com.example.smarthiring.common.vo;

import com.example.smarthiring.dto.CapabilityLevelDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserSkills {
    private Integer profileId;
    private List<CapabilityLevelDto> capabilityLevelDtos;
}