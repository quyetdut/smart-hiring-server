package com.example.smarthiring.service.implement;

import com.example.smarthiring.entity.Division;
import com.example.smarthiring.repository.DivisionRepository;
import com.example.smarthiring.service.DivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;
    @Override
    public List<Division> getAllDivision() {
        return divisionRepository.findAll();
    }
}
