package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.PositionRequestDto;
import com.smartdev.iresource.personal.entity.Position;

import java.util.List;

public interface PositionService {

    Boolean createPosition(PositionRequestDto positionRequestDto);

    List<Position> getListPostion();

    Position getPositionById(Integer id);

    Boolean getPositionByIdForProjectservice(Integer id);

    List<Position> getListPositionByRole(String role);

    List<Position> getPositionByProfileId(Integer userId);
}
