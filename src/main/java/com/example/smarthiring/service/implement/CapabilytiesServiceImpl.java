package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.dto.CapabilityDto;
import com.smartdev.iresource.personal.entity.Capabilities;
import com.smartdev.iresource.personal.entity.PositionCapabilities;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.exceptions.NotFoundException;
import com.smartdev.iresource.personal.repository.CapabilitiesRepository;
import com.smartdev.iresource.personal.repository.PositionCapabilitiesRepository;
import com.smartdev.iresource.personal.repository.PositionRepository;
import com.smartdev.iresource.personal.services.CapabilytiesService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CapabilytiesServiceImpl implements CapabilytiesService {

    @Autowired
    private CapabilitiesRepository capabilitiesRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private PositionCapabilitiesRepository positionCapabilitiesRepository;

    @Override
    public List<CapabilityDto> getListCapability() {
        try {
            List<Capabilities> capabilities = capabilitiesRepository.findAll();
            List<CapabilityDto> capabilityDtos = new ArrayList<>();
            for ( Capabilities capabilitiesItem : capabilities ){
                CapabilityDto capabilityDto = new CapabilityDto();
                capabilityDto.setId(capabilitiesItem.getId());
                capabilityDto.setName(capabilitiesItem.getName());
                capabilityDtos.add(capabilityDto);
            }
            return capabilityDtos;
        } catch (Exception e){
            throw new NotFoundException(ExceptionDefinition.CAPABILITY_NOT_FOUND.getMessage(), ExceptionDefinition.CAPABILITY_NOT_FOUND.getErrorCode());
        }
    }

    @Override
    public List<CapabilityDto> getListCapabilityByPositionId(Integer positionId) {
        List<CapabilityDto> result = new ArrayList<>();
        try {
            List<PositionCapabilities> positionCapabilitiesList = positionCapabilitiesRepository.findByPositionId(positionId);

            if (CollectionUtils.isNotEmpty(positionCapabilitiesList)) {
                capabilitiesRepository.findByIdIn(positionCapabilitiesList.stream().map(ps -> ps.getCapabilitiesId())
                        .collect(Collectors.toList())).forEach( capability -> {
                    CapabilityDto capabilityDto = new CapabilityDto();
                    capabilityDto.setId(capability.getId());
                    capabilityDto.setName(capability.getName());
                    result.add(capabilityDto);
                });
            }
        } catch (Exception e) {
            throw new FailException(ExceptionDefinition.CAPABILITY_NOT_FOUND.getMessage(), ExceptionDefinition.USER_NOT_FOUND.getErrorCode());
        }

        return result;
    }

    @Override
    public Capabilities getCapabilityByid(Integer id) {
        try {
            Optional<Capabilities> capabilities = capabilitiesRepository.findById(id);
            return capabilities.get();
        } catch (Exception e) {
            throw new NotFoundException(ExceptionDefinition.CAPABILITY_NOT_FOUND.getMessage(), ExceptionDefinition.CAPABILITY_NOT_FOUND.getErrorCode());
        }
    }

    @Override
    public String getCapabilityNames(Collection<Integer> ids) {
        List<Capabilities> capabilitiesList = capabilitiesRepository.findByIdIn(ids);

        if (CollectionUtils.isNotEmpty(capabilitiesList)) {
            StringUtils.join(capabilitiesList, ',');
        }

        return "";
    }
}
