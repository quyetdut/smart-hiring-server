package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.dto.LocationRequestDto;
import com.smartdev.iresource.personal.entity.Locations;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.repository.LocationRepository;
import com.smartdev.iresource.personal.services.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Boolean createLocation(LocationRequestDto locationRequestDto){
        try {
            Locations locations = new Locations();
            locations.setCity(locationRequestDto.getCity());
            locations.setTimeZone(locationRequestDto.getTimeZone());
            locationRepository.save(locations);
            return true;
        } catch (Exception e) {
            throw new FailException(ExceptionDefinition.CREATE_LOCATION_FAIL.getMessage(), ExceptionDefinition.CREATE_LOCATION_FAIL.getErrorCode());
        }
    }

    @Override
    public List<Locations> getAllLocation(){
        return locationRepository.findAll();
    }

}
