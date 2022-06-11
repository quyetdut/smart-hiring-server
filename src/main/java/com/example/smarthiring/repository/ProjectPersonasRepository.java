package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectPersonas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectPersonasRepository extends JpaRepository<ProjectPersonas, Integer> {
    Set<ProjectPersonas> findAllByProjectId(Integer id);

    void deleteAllByProjectId(Integer projectId);

    Optional<ProjectPersonas> findByProjectIdAndPositionId(Integer projectId, Integer positionId);

    Set<ProjectPersonas> findAllByIdIn(Collection<Integer> ids);

}
