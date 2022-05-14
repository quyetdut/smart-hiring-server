package com.example.smarthiring.service;


import com.example.smarthiring.dto.CapabilityDto;
import com.example.smarthiring.entity.Capabilities;

import java.util.Collection;
import java.util.List;

public interface CapabilytiesService {
    List<CapabilityDto> getListCapability();

    List<CapabilityDto> getListCapabilityByPositionId(Integer personaId);

    Capabilities getCapabilityByid(Integer id);

    String getCapabilityNames(Collection<Integer> ids);
}
