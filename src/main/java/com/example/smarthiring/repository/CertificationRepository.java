package com.smartdev.iresource.personal.repository;

import com.smartdev.iresource.personal.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    List<Certification> findByProfileId(Integer profileId);
}
