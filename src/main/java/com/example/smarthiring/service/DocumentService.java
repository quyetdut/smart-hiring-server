package com.example.smarthiring.service;

import com.example.smarthiring.dto.DocumentDeleteDto;
import com.example.smarthiring.dto.DocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface DocumentService {
    List<DocumentDto> saveDocuments(Set<MultipartFile> documents, Integer projectId);
    List<DocumentDto> getProjectDocuments(Integer projectId);
    boolean deleteDocument(DocumentDeleteDto documentDeleteDto);
}
