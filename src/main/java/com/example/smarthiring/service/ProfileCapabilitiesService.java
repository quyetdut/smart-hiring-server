package com.example.smarthiring.service;

import com.example.smarthiring.dto.CapabilityLevelDto;

import java.util.List;

public interface ProfileCapabilitiesService {
    boolean createAndUpdate(Integer profileId, List<CapabilityLevelDto> capabilitiesLevel);
}
