package com.example.smarthiring.repository;

import com.example.smarthiring.entity.WorkExperiences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface WorkExperienceRepository extends JpaRepository<WorkExperiences, Integer> {
    List<WorkExperiences> findByProfileId(Integer profileId);
}
