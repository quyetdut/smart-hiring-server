package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.dto.AwardDto;
import com.smartdev.iresource.personal.entity.Awards;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.exceptions.NotFoundException;
import com.smartdev.iresource.personal.repository.AwardRepository;
import com.smartdev.iresource.personal.services.AwardService;
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
            throw new NotFoundException(ExceptionDefinition.AWARD_NOT_FOUND.getMessage(), ExceptionDefinition.AWARD_NOT_FOUND.getErrorCode());
        }
    }

    @Override
    public Boolean deleteAwardById(Integer id) {
        try {
            awardRepository.deleteAwardsById(id);
            return true;
        }catch (Exception e){
            throw new NotFoundException(ExceptionDefinition.DELETE_AWARD_FAIL.getMessage(), ExceptionDefinition.DELETE_AWARD_FAIL.getErrorCode());
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
