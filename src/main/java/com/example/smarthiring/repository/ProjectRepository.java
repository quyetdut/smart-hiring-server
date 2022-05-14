package com.example.smarthiring.repository;
import com.example.smarthiring.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findByProjectNameContaining(String projectName, Pageable pageable);

    Page<Project> findAllByPoIdAndProjectNameContaining(Integer poId, String projectName, Pageable pageable);

    List<Project> findAllByPoIdAndProjectNameContaining(Integer poId, String projectName);

    List<Project> findAllByPoId(Integer poId);

    Optional<Project> findById(Integer projectId);

    List<Project> findByIdIn(List<Integer> projectIds);
}
