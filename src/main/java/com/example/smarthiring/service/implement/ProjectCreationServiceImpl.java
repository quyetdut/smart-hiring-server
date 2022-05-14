package com.example.smarthiring.service.implement;

import com.example.smarthiring.converter.PersonaConverter;
import com.example.smarthiring.converter.ProjectGetAllConverter;
import com.example.smarthiring.dto.ProjectCreationDTO;
import com.example.smarthiring.dto.ProjectInfoDTO;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.entity.*;
import com.example.smarthiring.entity.Process;
import com.example.smarthiring.exception.FileStorageException;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.exception.SomethingWrongException;
import com.example.smarthiring.repository.*;
import com.example.smarthiring.service.ProjectCreationService;
import com.example.smarthiring.service.ServiceUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.activation.MimetypesFileTypeMap;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.smarthiring.common.ErrorLog.*;
import static com.example.smarthiring.common.ResponseMessage.CREATE_PROJECT_FAILED;
import static com.example.smarthiring.common.ResponseMessage.INTERNAL_SERVER_ERROR;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProjectCreationServiceImpl implements ProjectCreationService {

    private final ProjectRepository projectRepository;
    private final DocumentRepository documentRepository;
    private final ToolRepository toolRepository;
    private final ProcessRepository processRepository;
    private final ProjectToolRepository projectToolRepository;
    private final ProjectProcessRepository projectProcessRepository;
    private final PersonasTechnicalRepository personasTechnicalRepository;
    private final ProjectPersonasRepository projectPersonasRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final FileServiceImpl fileStorageService;

    private final PositionRepository positionRepository;
    private final ServiceUtil serviceUtil;

    @Override
    public Boolean createProject(Integer poId, ProjectCreationDTO projectCreationDTO, MultipartFile imageFile, MultipartFile[] documents) {
        try {
            if (!serviceUtil.isExitPOId(poId))
                throw new NotFoundException("PO account (id: " + poId + ") is not exist to Create project");

            ProjectInfoDTO projectInfoDTO = projectCreationDTO.getProjectInfo();
            Project project = ProjectGetAllConverter.toEntity(projectInfoDTO);
            project.setPoId(poId);
            project = projectRepository.save(project);
            Integer projectId = project.getId();

            fileStorageService.initProjectFolderByProjectId(projectId);
            if (imageFile != null) {
                if (!isImageType(imageFile))
                    throw new FileStorageException("filename: " + imageFile.getOriginalFilename() + " is not a image");
                project.setImgPath(saveProjectImageService(projectId, imageFile));
            }

            // save something else in project
            Set<Tool> tools = projectCreationDTO.getTools();
            Set<Process> processes = projectCreationDTO.getProcesses();
            Set<ProjectPersonasDTO> projectPersonas = projectCreationDTO.getProjectPersonas();
            Set<ProjectMember> members = projectCreationDTO.getMembers();

            saveProjectTools(projectId, tools);
            saveProjectProcesses(projectId, processes);
            saveProjectPersonas(projectId, projectPersonas);
            saveProjectMembers(projectId, members);

            if (documents != null) {
                this.saveProjectDocumentService(new HashSet<>(Arrays.asList(documents)), project.getId());
            }

            return true;
        } catch (NotFoundException e) {
            log.info(CREATE_PROJECT_FAILED);
            e.printStackTrace();
            throw new NotFoundException(CREATE_PROJECT_FAILED);
        } catch (FileStorageException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isImageType(MultipartFile imageFile) {
        MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
        String mimeType = fileTypeMap.getContentType(imageFile.getOriginalFilename());
        log.info("filename: {}", imageFile.getOriginalFilename());
        if (mimeType != null && mimeType.split("/")[0].equals("image"))
            return true;
        return false;
    }

    public String saveProjectImageService(Integer projectId, MultipartFile file) {
        fileStorageService.deleteProjectImage(projectId);
        return fileStorageService.saveProjectImage(projectId, file);
    }

    public void saveProjectDocumentService(Set<MultipartFile> documents, Integer projectId) {
        documentRepository.deleteAllByProjectId(projectId);
        fileStorageService.deleteProjectDocument(projectId);

        Set<String> documentPaths = new HashSet<>();
        documents.forEach(document -> documentPaths.add(
                fileStorageService.saveProjectDocument(projectId, document)
        ));

        this.saveProjectDocuments(
                projectId,
                documentPaths
                    .stream()
                    .map(documentPath -> {
                        Document document = new Document();
                        document.setProjectId(projectId);
                        document.setFilePath(documentPath);
                        documentRepository.save(document);
                        return document;
                    }).collect(Collectors.toSet())
        );
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void saveProjectMembers(Integer projectId, Set<ProjectMember> members) {

        try {
            List<ProjectMember> projectMembers = new ArrayList<>();

            if (!members.isEmpty()) {
                members.forEach(member -> {
                    if (serviceUtil.isExistUserById(member.getUserId())) {
                        member.setProjectId(projectId);
                        projectMembers.add(member);
                    }
                });
            }

            if (!projectMembers.isEmpty()) {
                List<ProjectMember> test = projectMemberRepository.saveAll(projectMembers);
            }
        } catch (Exception e) {
            log.error(SAVE_PROJECT_MEMBER_FAILED);
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void saveProjectPersonas(Integer projectId, Set<ProjectPersonasDTO> projectPersonas) {

        try {
            if (!projectPersonas.isEmpty()) {
                projectPersonas.forEach(position -> {
                    if (positionRepository.existsById(position.getPositionId())) {
                        position.setProjectId(projectId);
                        ProjectPersonas personas = (ProjectPersonas) PersonaConverter.toEntity(position).get("projectPersonas");
                        Set<PersonasTechnical> personasTechnicals =
                                (Set<PersonasTechnical>) PersonaConverter
                                        .toEntity(position)
                                        .get("technicals");

                        personas.setProjectId(projectId);
                        personas = projectPersonasRepository.save(personas);

                        savePersonasTechnicals(personas.getId(), personasTechnicals);
                    }
                });
            }
        } catch (Exception e) {
            log.error(SAVE_PROJECT_PERSONAS_FAILED);
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void savePersonasTechnicals(Integer projectPersonasId, Set<PersonasTechnical> personaTechnicals) {

        try {
            List<PersonasTechnical> technicals = new ArrayList<>();
            if (!personaTechnicals.isEmpty()) {
                personaTechnicals.forEach(technical -> {
                    if (personasTechnicalRepository.existsPersonasTechnicalByCapabilitiesId(technical.getCapabilitiesId())) {
                        technical.setProjectPersonasId(projectPersonasId);
                        technicals.add(technical);
                    }
                });
            }
            if (!technicals.isEmpty()) {
                personasTechnicalRepository.saveAll(technicals);
            }
        } catch (Exception e) {
            log.error(SAVE_PROJECT_TECHNICAL_FAILED);
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void saveProjectProcesses(Integer projectId, Set<Process> processes) {

        try {
            if (!processes.isEmpty()) {
                List<ProjectProcess> projectProcesses = new ArrayList<>();

                processes.forEach(process -> {
                    Optional<Process> checkExistProcess = checkProcessExist(process.getProcessName());
                    ProjectProcess projectProcess = new ProjectProcess();

                    if (checkExistProcess.isEmpty()) {
                        process = processRepository.save(process);

                    } else {
                        process = checkExistProcess.get();
                    }

                    projectProcess.setProjectId(projectId);
                    projectProcess.setProcessId(process.getId());

                    projectProcesses.add(projectProcess);
                });

                if (!projectProcesses.isEmpty()) {
                    projectProcessRepository.saveAll(projectProcesses);
                }
            }
        } catch (Exception e) {
            log.error(SAVE_PROCESS_FAILED);
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void saveProjectTools(Integer projectId, Set<Tool> tools) {

        try {
            if (!tools.isEmpty()) {

                List<ProjectTool> projectTools = new ArrayList<>();


                tools.forEach(tool -> {
                    Optional<Tool> checkExistTool = checkToolExist(tool.getToolName());
                    ProjectTool projectTool = new ProjectTool();

                    if (checkExistTool.isEmpty()) {
                        tool = toolRepository.save(tool);
                    } else {
                        tool = checkExistTool.get();
                    }

                    projectTool.setProjectId(projectId);
                    projectTool.setToolId(tool.getId());

                    projectTools.add(projectTool);
                });

                if (!projectTools.isEmpty()) {
                    projectToolRepository.saveAll(projectTools);
                }
            }
        } catch (Exception e) {
            log.error(SAVE_TOOL_FAILED);
        }
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = Exception.class
    )
    void saveProjectDocuments(Integer projectId, Set<Document> documents) {

        try {
            if (!documents.isEmpty()) {
                documents.forEach(document -> {
                    document.setProjectId(projectId);
                });

                documentRepository.saveAll(documents);
            }
        } catch (Exception e) {
            log.error(SAVE_DOCUMENT_FAILED);
        }
    }

    Optional<Process> checkProcessExist(String processName) {

        return processRepository.findByProcessName(processName);
    }

    Optional<Tool> checkToolExist(String toolName) {

        return toolRepository.findByToolName(toolName);
    }

}
