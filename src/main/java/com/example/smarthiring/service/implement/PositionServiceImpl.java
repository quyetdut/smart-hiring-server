package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.dto.PositionRequestDto;
import com.smartdev.iresource.personal.entity.Position;
import com.smartdev.iresource.personal.entity.ProfilePosition;
import com.smartdev.iresource.personal.entity.Profiles;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.exceptions.NotFoundException;
import com.smartdev.iresource.personal.repository.PositionRepository;
import com.smartdev.iresource.personal.repository.ProfilePositionRepository;
import com.smartdev.iresource.personal.repository.ProfileRepository;
import com.smartdev.iresource.personal.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private ProfilePositionRepository profilePositionRepository;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Boolean createPosition(PositionRequestDto positionRequestDto) {
        Optional<Position> personas = positionRepository.findByName(positionRequestDto.getName());
        if (personas.isEmpty()){
            Position positionNew = new Position();
            positionNew.setName(positionRequestDto.getName());
            positionNew.setIcon(positionRequestDto.getIcon());
            try {
                positionRepository.save(positionNew);
                return true;
            }catch (Exception e){
                throw new FailException(ExceptionDefinition.CREATE_POSITION_FAIL.getMessage(), ExceptionDefinition.CREATE_POSITION_FAIL.getErrorCode());
            }
        }else {
            throw new FailException(ExceptionDefinition.POSITION_ALREADY_EXIST.getMessage(), ExceptionDefinition.POSITION_ALREADY_EXIST.getErrorCode());
        }

    }

    @Override
    public List<Position> getListPostion() {
        return positionRepository.findAll();
    }

    @Override
    public Position getPositionById(Integer id) {
        try {
            Optional<Position> position = positionRepository.findById(id);
            return position.get();
        }catch (Exception e) {
            throw new NotFoundException(ExceptionDefinition.POSITION_NOT_FOUND.getMessage(), ExceptionDefinition.POSITION_NOT_FOUND.getErrorCode());
        }
    }

    @Override
    public Boolean getPositionByIdForProjectservice(Integer id) {
        Optional<Position> position = positionRepository.findById(id);
        if (position.isEmpty()){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public List<Position> getListPositionByRole(String role) {
        return positionRepository.findByRoleName( role);
    }

    @Override
    public List<Position> getPositionByProfileId(Integer userId) {
        Profiles profiles = profileRepository.findByUserId(userId)
                .orElseThrow(() ->  new FailException(ExceptionDefinition.USER_NOT_FOUND.getMessage(), ExceptionDefinition.USER_NOT_FOUND.getErrorCode()));
        List<ProfilePosition> profilePositions = profilePositionRepository.findByProfileId(profiles.getId());
        List<Position> positions = new ArrayList<>();
        for (ProfilePosition profilePosition :profilePositions){
            Position position = positionRepository.findById(profilePosition.getPositionId()).get();
            positions.add(position);
        };
        return positions;
    }
}
