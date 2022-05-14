package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.dto.CertificationDto;
import com.smartdev.iresource.personal.entity.Certification;
import com.smartdev.iresource.personal.repository.CertificationRepository;
import com.smartdev.iresource.personal.services.CertifcationService;
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
