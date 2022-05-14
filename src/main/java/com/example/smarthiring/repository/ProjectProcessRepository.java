package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.entity.ProjectProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectProcessRepository extends JpaRepository<ProjectProcess, Integer> {
    List<ProjectProcess> findAllByProjectId(Integer projectId);

    void deleteAllByProjectId(Integer id);
}
