package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.entity.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Integer> {
    Optional<Tool> findByToolName(String toolName);

    Optional<Tool> findById(Integer id);

}
