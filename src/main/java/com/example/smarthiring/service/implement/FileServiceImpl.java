package com.example.smarthiring.service.implement;

import com.example.smarthiring.exception.FileStorageException;
import com.example.smarthiring.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    private final Path ROOT_FILE_PATH = Paths.get("/home/qmait/iresource/project");
    private final String IMAGE_PATH = "image";
    private final String DOCUMENT_PATH = "document";
    private final String SAVE_PATH = "/project/fileStore";
    private final String PATH_IMAGE_REQUEST = "/get-image?projectId=";
    private final String PATH_DOCUMENT_REQUEST_PROJECTID_PARAM = "/get-document?projectId=";
    private final String PATH_DOCUMENT_REQUEST_FILENAME_PARAM = "&filename=";
    private final Long SIZE_LIMIT_DOCUMENT = 10000000L;
    private final Long SIZE_LIMIT_IMAGE = 5000000L;

    @Override
    public void init() {
        try {
            Files.createDirectory(ROOT_FILE_PATH);
        } catch (FileAlreadyExistsException ex) {
            log.warn("FileStorageService: root file already exist, but still have to initialize");
        } catch (IOException ex) {
            throw new FileStorageException("can not initialize project path: " + ex.getClass());
        }
    }

    @Override
    public void initProjectFolderByProjectId(Integer projectId) {
        try {
            Path pathToCreate = ROOT_FILE_PATH.resolve(projectId.toString());
            Files.createDirectory(pathToCreate);
            Files.createDirectory(pathToCreate.resolve(IMAGE_PATH));
            Files.createDirectory(pathToCreate.resolve(DOCUMENT_PATH));
        } catch (FileAlreadyExistsException ex) {
            ex.printStackTrace();
            log.warn("FileStorageService(projectID-{}): root file already exist, but still have to initialize", projectId);
        } catch (IOException ex) {
            ex.printStackTrace();
            log.error("can not initialize folder for projectId-{}, path-{}", projectId, ex.getClass());
            throw new FileStorageException("can not initialize folder for project: " + ex.getClass());
        }
    }

    @Override
    public void save(Path filePath, MultipartFile file) {
        try {
            Path pathToSave = filePath.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), pathToSave);
        } catch (FileAlreadyExistsException e) {
            throw new FileStorageException("failed: file " + file.getOriginalFilename() + " is existed");
        } catch (NoSuchFileException ex) {
            log.error("FileStorageService: initialize root folder " + filePath);
        } catch (PermissionDeniedDataAccessException ex) {
            throw new FileStorageException("do not have permission to access data");
        } catch (Exception e) {
            throw new FileStorageException("error while save file: " + e.getClass());
        }
    }

    public Resource load(Path pathToLoad ,String filename) {
        try {
            Path file = pathToLoad.resolve(filename);
            log.info("file: {}", file);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageException("could not read the file, or file is not exist!");
            }
        } catch (MalformedURLException e) {
            throw new FileStorageException("error while load file: " + e.getMessage());
        }
    }

    @Override
    public void deleteAllIn(Path pathToDelete) {
        FileSystemUtils.deleteRecursively(pathToDelete.toFile());
    }

    @Override
    public Set<Path> loadAll(Path pathToLoadAll) {
        try {
            return Files.walk(this.ROOT_FILE_PATH, 1)
                    .filter(path -> !path.equals(this.ROOT_FILE_PATH))
                    .map(this.ROOT_FILE_PATH::relativize).collect(Collectors.toSet());
        } catch (IOException e) {
            throw new FileStorageException("Could not load the files!");
        }
    }


    @Override
    public String saveProjectImage(Integer projectId, MultipartFile file) {

        // check size of file must be smaller 5MB
        if (file.getSize() > SIZE_LIMIT_IMAGE) {
            log.info("size of {}: {}", file.getOriginalFilename(), file.getSize());
            throw new FileStorageException("the size of image must be smaller than 5MB");
        }

        Path projectPath = ROOT_FILE_PATH.resolve(projectId.toString());
        Path imagePath = projectPath.resolve(IMAGE_PATH);

        this.save(imagePath, file);

        String imageFilePath = SAVE_PATH
                .concat(PATH_IMAGE_REQUEST)
                .concat(projectId.toString());

        return imageFilePath;
    }

    @Override
    public String saveProjectDocument(Integer projectId, MultipartFile file) {

        // check size of file must be smaller 10MB
        if (file.getSize() > SIZE_LIMIT_DOCUMENT) {
            log.info("size of {}: {}", file.getOriginalFilename(), file.getSize());
            throw new FileStorageException("the size of each document must be smaller than 10MB");
        }

        Path projectPath = ROOT_FILE_PATH.resolve(projectId.toString());
        Path documentPath = projectPath.resolve(DOCUMENT_PATH);

        this.save(documentPath, file);

        return SAVE_PATH
                .concat(PATH_DOCUMENT_REQUEST_PROJECTID_PARAM).concat(projectId.toString())
                .concat(PATH_DOCUMENT_REQUEST_FILENAME_PARAM).concat(file.getOriginalFilename());
    }

    @Override
    public void deleteProjectImage(Integer projectId) {
        Path pathToDelete = ROOT_FILE_PATH.resolve(IMAGE_PATH);
        deleteAllIn(pathToDelete);
    }

    @Override
    public void deleteProjectDocument(Integer projectId) {
        Path pathToDelete = ROOT_FILE_PATH.resolve(DOCUMENT_PATH);
        deleteAllIn(pathToDelete);
    }

    @Override
    public void deleteDocument(Integer projectId, String fileName) {
        Path pathToDelete = ROOT_FILE_PATH.resolve(DOCUMENT_PATH).resolve(Integer.toString(projectId)).resolve(fileName);
        deleteAllIn(pathToDelete);
    }

    @Override
    public Resource getProjectDocument(Integer projectId, String filename) {
        Path filePath = ROOT_FILE_PATH
                .resolve(projectId.toString())
                .resolve(DOCUMENT_PATH);
        return load(filePath, filename);
    }

    @Override
    public Resource getProjectImage(Integer projectId) {
        Path filePath = ROOT_FILE_PATH
                .resolve(projectId.toString())
                .resolve(IMAGE_PATH);

        try {
            Optional<Path> first = Files.list(filePath).findFirst();
            if (first.isPresent()) return load(filePath, first.get().getFileName().toString());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("message: {}", e.getMessage() + e.getClass());
        }

        return null;
    }
}
