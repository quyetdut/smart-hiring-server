package com.example.smarthiring.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  com.example.smarthiring.entity.Process;
import java.util.Optional;

@Repository
public interface ProcessRepository extends JpaRepository<Process, Integer> {
    Optional<Process> findByProcessName(String processName);

    Optional<Process> findById(Integer id);
}

