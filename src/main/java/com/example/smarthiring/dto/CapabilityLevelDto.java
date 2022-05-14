package com.example.smarthiring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CapabilityLevelDto {
    private Integer capabilityId;
    private String name;
    private Integer level;
    private Integer importance;
}
