package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.dto.CapabilityLevelDto;
import com.smartdev.iresource.personal.entity.ProfileCapabilities;
import com.smartdev.iresource.personal.repository.ProfileCapabilitiesRepository;
import com.smartdev.iresource.personal.services.ProfileCapabilitiesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ProfileCapabilitiesServiceImpl implements ProfileCapabilitiesService {

    @Autowired
    private ProfileCapabilitiesRepository profileCapabilitiesRepository;

    @Override
    public boolean createAndUpdate(Integer profileId, List<CapabilityLevelDto> capabilitiesLevel) {
        profileCapabilitiesRepository.deleteAllByProfileId(profileId);
        List<ProfileCapabilities> profileCapabilitiesList = new ArrayList<>();
        capabilitiesLevel.forEach(cl -> {
            ProfileCapabilities profileCapabilities = new ProfileCapabilities();
            profileCapabilities.setProfileId(profileId);
            profileCapabilities.setLevel(cl.getLevel());
            profileCapabilities.setCapabilitiesId(cl.getCapabilityId());
            profileCapabilitiesList.add(profileCapabilities);
        });

        try {
            profileCapabilitiesRepository.saveAll(profileCapabilitiesList);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }
}
