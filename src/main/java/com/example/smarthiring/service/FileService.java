package com.smartdev.iresource.project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface FileService {
    void init();

    void initProjectFolderByProjectId(Integer projectId);

    void save(Path filePath, MultipartFile file);

    void deleteAllIn(Path pathToDelete);

    void deleteProjectImage(Integer projectId);

    void deleteProjectDocument(Integer projectId);

    void deleteDocument(Integer projectId, String fileName);

    Set<Path> loadAll(Path pathToLoadAll);

    String saveProjectImage(Integer projectId, MultipartFile file);

    String saveProjectDocument(Integer projectId, MultipartFile file);

    Resource getProjectDocument(Integer projectId, String filename);

    Resource getProjectImage(Integer projectImage) throws IOException;
}
