package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.LocationRequestDto;
import com.example.smarthiring.entity.Locations;
import com.example.smarthiring.enums.ExceptionDefinition;
import com.example.smarthiring.exception.FailException;
import com.example.smarthiring.repository.LocationRepository;
import com.example.smarthiring.service.LocationService;
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
