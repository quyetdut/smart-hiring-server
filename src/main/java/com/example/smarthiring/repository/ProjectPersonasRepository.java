package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.entity.ProjectPersonas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectPersonasRepository extends JpaRepository<ProjectPersonas, Integer> {
    Set<ProjectPersonas> findAllByProjectId(Integer id);

    void deleteAllByProjectId(Integer projectId);

    Optional<ProjectPersonas> findByProjectIdAndPositionId(Integer projectId, Integer positionId);

}
