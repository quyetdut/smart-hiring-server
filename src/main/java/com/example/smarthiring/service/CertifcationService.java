package com.example.smarthiring.services;

import com.smartdev.iresource.personal.dto.CertificationDto;

public interface CertifcationService {
    CertificationDto createAndUpdate(Integer profileId, CertificationDto certificationDto);

    Boolean deleteCertification(Integer certificationId);
}
