package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.dto.CapabilityRequestDto;
import com.smartdev.iresource.personal.entity.Capabilities;
import com.smartdev.iresource.personal.entity.Position;
import com.smartdev.iresource.personal.entity.PositionCapabilities;
import com.smartdev.iresource.personal.exceptions.NotFoundException;
import com.smartdev.iresource.personal.repository.CapabilitiesRepository;
import com.smartdev.iresource.personal.repository.PositionCapabilitiesRepository;
import com.smartdev.iresource.personal.repository.PositionRepository;
import com.smartdev.iresource.personal.services.PositionCapabilitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PositionCapabilitiesServiceImpl implements PositionCapabilitiesService {

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CapabilitiesRepository capabilitiesRepository;

    @Autowired
    private PositionCapabilitiesRepository positionCapabilitiesRepository;

    @Override
    public boolean createPositionCapabilities(CapabilityRequestDto capabilityRequestDto) {
        Position position = positionRepository.findById(capabilityRequestDto.getPositionId())
                .orElseThrow(() -> new NotFoundException(ExceptionDefinition.POSITION_NOT_FOUND.getMessage() , ExceptionDefinition.POSITION_NOT_FOUND.getErrorCode()));

        Capabilities capabilities = capabilitiesRepository.findByName(capabilityRequestDto.getName())
                .orElseThrow(() -> new NotFoundException(ExceptionDefinition.CAPABILITY_NOT_FOUND.getMessage(), ExceptionDefinition.CAPABILITY_NOT_FOUND.getErrorCode()));

        PositionCapabilities positionCapabilities = new PositionCapabilities();
        positionCapabilities.setPositionId(position.getId());
        positionCapabilities.setCapabilitiesId(capabilities.getId());

        try {
            positionCapabilitiesRepository.save(positionCapabilities);
            return true;
        } catch(Exception ex) {
            return false;
        }
    }
}
