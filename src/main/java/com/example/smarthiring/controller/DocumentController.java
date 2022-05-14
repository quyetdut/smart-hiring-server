package com.smartdev.iresource.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartdev.iresource.project.common.enums.ResponseResult;
import com.smartdev.iresource.project.common.response.ResponseHandler;
import com.smartdev.iresource.project.dto.DocumentDeleteDto;
import com.smartdev.iresource.project.dto.DocumentDto;
import com.smartdev.iresource.project.dto.ProjectInfoDTO;
import com.smartdev.iresource.project.exception.FileStorageException;
import com.smartdev.iresource.project.service.DocumentService;
import com.smartdev.iresource.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@Slf4j
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/create-and-update")
    public ResponseEntity<Object> createProject(
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "documents") MultipartFile[] documents
    ) {
        try {
            Set<MultipartFile> setDocument = new HashSet<MultipartFile>(Arrays.asList(documents));
            List<DocumentDto> result = documentService.saveDocuments(setDocument, projectId);

            return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
        } catch (Exception ex) {
            log.error("Create document failed: {} {}", ex.getClass(), ex.getMessage());
            throw new FileStorageException("error: " + ex.getClass());
        }
    }

    @GetMapping("/get-documents/{projectId}")
    public ResponseEntity<Object> getProjectDocuemnts(@PathVariable("prjectId") Integer projectId) {
        try {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(documentService.getProjectDocuments(projectId), ResponseResult.SUCCESS));
        } catch (Exception ex) {
            log.error("Get project failed: {} {}", ex.getClass(), ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/delete-document")
    public ResponseEntity<Object> deleteDocument(@RequestParam("documentInfo") DocumentDeleteDto documentDeleteDto) {
        return ResponseEntity.ok(ResponseHandler.getInstance().response(documentService.deleteDocument(documentDeleteDto), ResponseResult.SUCCESS));
    }
}
