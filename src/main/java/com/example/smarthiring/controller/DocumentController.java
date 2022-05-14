package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.DocumentDeleteDto;
import com.example.smarthiring.dto.DocumentDto;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.exception.FileStorageException;
import com.example.smarthiring.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/project/document")
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
