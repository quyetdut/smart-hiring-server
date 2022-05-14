package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.AwardDto;
import com.smartdev.iresource.personal.entity.Awards;

import java.util.List;


public interface AwardService {
    Awards findByAwardId(Integer id);

    Boolean createAward(AwardDto awardDto);

    List<Awards> getAllAward();

    Boolean deleteAwardById(Integer id);

    Awards updateAwardById(AwardDto awardDto, Integer id);
}