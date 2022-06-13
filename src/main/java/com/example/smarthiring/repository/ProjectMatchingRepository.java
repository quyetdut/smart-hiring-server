package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectMatching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional(readOnly = true)
public interface ProjectMatchingRepository extends JpaRepository<ProjectMatching, Integer> {
    Optional<List<ProjectMatching>> findByProjectIdAndProfileId(Integer projectId, Integer profileId);

    Page<ProjectMatching> findAllByProfileId(Integer profileId, Pageable pageable);

    Page<ProjectMatching> findAllByProfileIdAndProjectNameContainsOrProfileIdAndSkillsContains(Integer id1, String projectName, Integer id2, String skill, Pageable pageable);

    List<ProjectMatching> findAllByProjectIdAndPositionId(Integer projectId, Integer positionId);

    List<ProjectMatching> findAllByProfileIdIn(Collection<Integer> profileIds);
}
