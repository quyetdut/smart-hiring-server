package com.example.smarthiring.service;


import com.example.smarthiring.dto.CertificationDto;

public interface CertifcationService {
    CertificationDto createAndUpdate(Integer profileId, CertificationDto certificationDto);

    Boolean deleteCertification(Integer certificationId);
}
