package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.CapabilityDto;
import com.smartdev.iresource.personal.entity.Capabilities;

import java.util.Collection;
import java.util.List;

public interface CapabilytiesService {
    List<CapabilityDto> getListCapability();

    List<CapabilityDto> getListCapabilityByPositionId(Integer personaId);

    Capabilities getCapabilityByid(Integer id);

    String getCapabilityNames(Collection<Integer> ids);
}
