package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.AwardDto;
import com.example.smarthiring.entity.Awards;
import com.example.smarthiring.enums.ExceptionDefinition;
import com.example.smarthiring.exception.FailException;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.repository.AwardRepository;
import com.example.smarthiring.service.AwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwardServiceImp  implements AwardService {
    @Autowired
    private AwardRepository awardRepository;

    @Override
    public Awards findByAwardId(Integer id) {
        Optional<Awards>  awards= awardRepository.findById(id);
        if(awards.isEmpty()){
            throw new FailException(ExceptionDefinition.AWARD_NOT_FOUND.getMessage(), ExceptionDefinition.AWARD_NOT_FOUND.getErrorCode());
        } else {
             return awards.get();
        }
    }

    @Override
    public Boolean createAward(AwardDto awardDto) {
            Awards awards = new Awards();
            awards.setName(awardDto.getName());
            awards.setImg(awardDto.getImg());
            try{
                awardRepository.save(awards);
                return true;
            }catch (Exception e){
                throw new FailException(ExceptionDefinition.CREATE_AWARD_FAIL.getMessage(), ExceptionDefinition.CREATE_AWARD_FAIL.getErrorCode());
            }
    }

    @Override
    public List<Awards> getAllAward() {
        try{
            List<Awards> awards = awardRepository.findAll();
            return awards;
        }catch (Exception e){
            throw new NotFoundException(ExceptionDefinition.AWARD_NOT_FOUND.getMessage(), ExceptionDefinition.CAPABILITY_NOT_FOUND.getErrorCode());
        }
    }

    @Override
    public Boolean deleteAwardById(Integer id) {
        try {
            awardRepository.deleteAwardsById(id);
            return true;
        }catch (Exception e){
            throw new NotFoundException(ExceptionDefinition.DELETE_AWARD_FAIL.getMessage(), ExceptionDefinition.CAPABILITY_NOT_FOUND.getErrorCode());
        }

    }

    @Override
    public Awards updateAwardById(AwardDto awardDto, Integer id) {
        Optional<Awards> awards = awardRepository.findById(id);
        if(awards.isEmpty()){
            throw new FailException(ExceptionDefinition.AWARD_NOT_FOUND.getMessage(), ExceptionDefinition.AWARD_NOT_FOUND.getErrorCode());
        }else {
            awards.get().setName(awardDto.getName());
            awards.get().setImg(awardDto.getImg());
            try{
                Awards newAward = awardRepository.save(awards.get());
                return newAward;
            }catch (Exception e){
                throw new FailException(ExceptionDefinition.UPDATE_AWARD_FAIL.getMessage(), ExceptionDefinition.UPDATE_AWARD_FAIL.getErrorCode());
            }
        }
    }
}
