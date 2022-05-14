package com.example.smarthiring.service;

import com.example.smarthiring.dto.AwardDto;
import com.example.smarthiring.entity.Awards;

import java.util.List;


public interface AwardService {
    Awards findByAwardId(Integer id);

    Boolean createAward(AwardDto awardDto);

    List<Awards> getAllAward();

    Boolean deleteAwardById(Integer id);

    Awards updateAwardById(AwardDto awardDto, Integer id);
}