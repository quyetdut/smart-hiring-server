package com.example.smarthiring.service.impl;

import com.smartdev.iresource.project.dto.DocumentDeleteDto;
import com.smartdev.iresource.project.dto.DocumentDto;
import com.smartdev.iresource.project.entity.Document;
import com.smartdev.iresource.project.exception.SomethingWrongException;
import com.smartdev.iresource.project.repository.DocumentRepository;
import com.smartdev.iresource.project.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.smartdev.iresource.project.common.ErrorLog.SAVE_DOCUMENT_FAILED;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final FileServiceImpl fileStorageService;

    @Override
    @Transactional(rollbackFor = SomethingWrongException.class)
    public List<DocumentDto> saveDocuments(Set<MultipartFile> documents, Integer projectId) {
        List<Document> documentList = new ArrayList<>();
        documents.forEach(document -> {
            Document doc = new Document();
            doc.setFilePath(fileStorageService.saveProjectDocument(projectId, document));
            doc.setName(document.getOriginalFilename());
            doc.setProjectId(projectId);
            documentList.add(doc);
        });

        List<DocumentDto> result = this.saveProjectDocuments(
                projectId,
                documentList
        );

        return result;
    }

    @Override
    public List<DocumentDto> getProjectDocuments(Integer projectId) {
        List<DocumentDto> result = new ArrayList<>();
        Set<Document> documents = documentRepository.findAllByProjectId(projectId);
        documents.forEach(doc -> {
            DocumentDto documentDto = new DocumentDto();
            documentDto.setId(doc.getId());
            documentDto.setFilePath(doc.getFilePath());
            result.add(documentDto);
        });

        return result;
    }

    @Override
    public boolean deleteDocument(DocumentDeleteDto documentDeleteDto) {
        try {
            documentRepository.deleteById(documentDeleteDto.getDocumentId());
            fileStorageService.deleteDocument(documentDeleteDto.getProjectId(), documentDeleteDto.getFileName());
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    private List<DocumentDto> saveProjectDocuments(Integer projectId, List<Document> documents) {
        List<DocumentDto> result = new ArrayList<>();

        try {
            if (!documents.isEmpty()) {
                documents.forEach(document -> {
                    document.setProjectId(projectId);
                });

                List<Document> documentList = documentRepository.saveAll(documents);
                if (!CollectionUtils.isEmpty(documentList)) {
                    documentList.forEach(doc -> {
                        DocumentDto documentDto = new DocumentDto();
                        documentDto.setId(doc.getId());
                        documentDto.setFilePath(doc.getFilePath());
                        documentDto.setName(doc.getName());
                        result.add(documentDto);
                    });
                }
            }
            return result;
        } catch (Exception e) {
            throw new SomethingWrongException(SAVE_DOCUMENT_FAILED);
        }
    }
}
