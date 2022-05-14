package com.example.smarthiring.service;


import com.example.smarthiring.dto.PositionRequestDto;
import com.example.smarthiring.entity.Position;

import java.util.List;

public interface PositionService {

    Boolean createPosition(PositionRequestDto positionRequestDto);

    List<Position> getListPostion();

    Position getPositionById(Integer id);

    Boolean getPositionByIdForProjectservice(Integer id);

    List<Position> getListPositionByRole(String role);

    List<Position> getPositionByProfileId(Integer userId);
}
