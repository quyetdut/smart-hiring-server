package com.smartdev.iresource.project.controller;


import com.google.common.net.HttpHeaders;
import com.smartdev.iresource.project.service.impl.FileServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
@RequestMapping("/fileStore")
@Slf4j
public class FileStorageController {

    FileServiceImpl fileStorageService;

    @GetMapping("/get-document")
    public ResponseEntity<Object> getProjectDocument(
            @RequestParam(value = "projectId") Integer projectId,
            @RequestParam(value = "filename") String filename) {
        try{
            Resource file = fileStorageService.getProjectDocument(projectId, filename);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFilename() + "\""
                    )
                    .body(file);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get-image")
    public ResponseEntity<Object> getProjectImage(@RequestParam(value = "projectId") Integer projectId) {
        try{
            Resource file = fileStorageService.getProjectImage(projectId);
            return ResponseEntity.ok()
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + file.getFilename() + "\""
                    )
                    .body(file);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
