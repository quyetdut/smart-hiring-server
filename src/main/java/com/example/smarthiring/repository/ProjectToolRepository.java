package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectTool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectToolRepository extends JpaRepository<ProjectTool, Integer> {
    List<ProjectTool> findAllByProjectId(Integer projectId);

    void deleteAllByProjectId(Integer id);
}
