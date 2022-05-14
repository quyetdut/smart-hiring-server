package com.example.smarthiring.service;


import com.example.smarthiring.dto.LocationRequestDto;
import com.example.smarthiring.entity.Locations;

import java.util.List;

public interface LocationService {
    Boolean createLocation(LocationRequestDto locationRequestDto);
    List<Locations> getAllLocation();
}
