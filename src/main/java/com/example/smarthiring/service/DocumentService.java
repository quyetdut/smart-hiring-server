package com.smartdev.iresource.project.service;

import com.smartdev.iresource.project.dto.DocumentDeleteDto;
import com.smartdev.iresource.project.dto.DocumentDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface DocumentService {
    List<DocumentDto> saveDocuments(Set<MultipartFile> documents, Integer projectId);
    List<DocumentDto> getProjectDocuments(Integer projectId);
    boolean deleteDocument(DocumentDeleteDto documentDeleteDto);
}
