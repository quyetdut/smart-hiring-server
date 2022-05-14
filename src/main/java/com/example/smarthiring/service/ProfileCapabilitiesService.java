package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.CapabilityLevelDto;

import java.util.List;

public interface ProfileCapabilitiesService {
    boolean createAndUpdate(Integer profileId, List<CapabilityLevelDto> capabilitiesLevel);
}
