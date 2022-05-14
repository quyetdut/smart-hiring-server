package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.CertificationDto;
import com.example.smarthiring.entity.Certification;
import com.example.smarthiring.repository.CertificationRepository;
import com.example.smarthiring.service.CertifcationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertifcationServiceImpl implements CertifcationService {

    @Autowired
    private CertificationRepository certificationRepository;

    @Override
    public CertificationDto createAndUpdate(Integer profileId, CertificationDto certificationDto) {
        Certification certification;
        if (certificationDto.getId() != null) {
            certification = certificationRepository.findById(certificationDto.getId()).get();
        } else {
            certification = new Certification();
        }

        certification.setName(certificationDto.getName());
        certification.setProfileId(profileId);

        certification = certificationRepository.save(certification);
        certificationDto.setId(certification.getId());
        return certificationDto;
    }

    @Override
    public Boolean deleteCertification(Integer certificationId) {
        try {
            certificationRepository.deleteById(certificationId);
            return true;
        } catch(Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
