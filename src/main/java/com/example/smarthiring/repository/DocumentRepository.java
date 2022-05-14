package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Set<Document> findAllByProjectId(Integer projectId);

    void deleteAllByProjectId(Integer projectId);
}
