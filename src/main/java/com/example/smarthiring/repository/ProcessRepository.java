package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.entity.Process;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    Optional<Process> findByProcessName(String processName);

    Optional<Process> findById(Integer id);
}

