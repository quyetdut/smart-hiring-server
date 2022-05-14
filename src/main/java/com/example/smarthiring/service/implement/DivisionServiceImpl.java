package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.entity.Division;
import com.smartdev.iresource.personal.repository.DivisionRepository;
import com.smartdev.iresource.personal.services.DivisionService;
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
