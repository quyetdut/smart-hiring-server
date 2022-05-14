package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectMatching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProjectMatchingRepository extends JpaRepository<ProjectMatching, Integer> {
    Optional<ProjectMatching> findByProjectIdAndProfileId(Integer projectId, Integer profileId);

    Page<ProjectMatching> findAllByProfileId(Integer profileId, Pageable pageable);

    Page<ProjectMatching> findAllByProfileIdAndProjectNameContainsOrProfileIdAndSkillsContains(Integer id1, String projectName, Integer id2, String skill, Pageable pageable);

    List<ProjectMatching> findAllByProjectIdAndPositionId(Integer projectId, Integer positionId);
}
