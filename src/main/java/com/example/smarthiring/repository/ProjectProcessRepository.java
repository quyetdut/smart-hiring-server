package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectProcessRepository extends JpaRepository<ProjectProcess, Integer> {
    List<ProjectProcess> findAllByProjectId(Integer projectId);

    void deleteAllByProjectId(Integer id);
}
