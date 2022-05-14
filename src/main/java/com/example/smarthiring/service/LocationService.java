package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.LocationRequestDto;
import com.smartdev.iresource.personal.entity.Locations;

import java.util.List;

public interface LocationService {
    Boolean createLocation(LocationRequestDto locationRequestDto);
    List<Locations> getAllLocation();
}
