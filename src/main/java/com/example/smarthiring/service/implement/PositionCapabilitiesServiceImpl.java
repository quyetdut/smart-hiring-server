package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.CapabilityRequestDto;
import com.example.smarthiring.entity.Capabilities;
import com.example.smarthiring.entity.Position;
import com.example.smarthiring.entity.PositionCapabilities;
import com.example.smarthiring.enums.ExceptionDefinition;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.repository.CapabilitiesRepository;
import com.example.smarthiring.repository.PositionCapabilitiesRepository;
import com.example.smarthiring.repository.PositionRepository;
import com.example.smarthiring.service.PositionCapabilitiesService;
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
