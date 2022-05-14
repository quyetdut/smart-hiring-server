package com.example.smarthiring.service.implement;

import com.example.smarthiring.common.ResponseMessage;
import com.example.smarthiring.common.vo.MatchingScore;
import com.example.smarthiring.common.vo.radarchart.object.MatchingSkill;
import com.example.smarthiring.converter.PersonaConverter;
import com.example.smarthiring.converter.PersonasTechnicalConvertor;
import com.example.smarthiring.converter.ProjectGetAllConverter;
import com.example.smarthiring.dto.*;
import com.example.smarthiring.entity.*;
import com.example.smarthiring.entity.Process;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.exception.SomethingWrongException;
import com.example.smarthiring.repository.*;
import com.example.smarthiring.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService, ResponseMessage {

    private final ProjectRepository projectRepository;
    private final ProjectPersonasRepository projectPersonasRepository;
    private final PersonasTechnicalRepository personasTechnicalRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final DocumentRepository documentRepository;
    private final ProjectUserStatusRepository projectUserStatusRepository;
    private final ProjectProcessRepository projectProcessRepository;
    private final ProjectToolRepository projectToolRepository;
    private final ProjectToolService projectToolService;
    private final ProjectProcessService projectProcessService;

    private final ProjectCreationServiceImpl projectCreationServiceImpl;
    private final FileServiceImpl fileStorageService;

    private final PositionService positionService;
    private final CapabilytiesService capabilytiesService;

    @Override
    public Map<String, Object> getAllProject(Integer poId, Integer page, String projectName) {
        try {
            List<ProjectResponseDTO> projects = new ArrayList<>();
            int totalPages;
            Map<String, Object> response = new HashMap<>();

            if (page != null) {
                Pageable paging = PageRequest.of(page, 2);
                Page<Project> projectPage;
                if (poId == null) {
                    projectPage = projectRepository.findByProjectNameContaining(projectName, paging);
                    for (Project project : projectPage) {
                        Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
                        Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
                        Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());
                        Set<ProjectPersonasDTO> projectPersonasResponseDTOS = new HashSet<>();

                        projectPersonas.forEach(projectPersonasItem -> {
                            ProjectPersonasDTO personasDTO;
                            Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
                            Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();
                            Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

                            projectTechnicals.forEach(technical -> {
                                Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                                technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
                            });

                            personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

                            projectPersonasResponseDTOS.add(personasDTO);
                        });
                        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
                        Set<Document> documents = documentRepository.findAllByProjectId(project.getId());
                        projects.add(ProjectGetAllConverter.toProjectCreationDTO(project, tools, processes, projectPersonasResponseDTOS,
                                projectMembers, documents));
                    }
                    totalPages = projectPage.getTotalPages();
                    response.put("project", projects);
                    response.put("totalPages", totalPages);
                } else {
                    projectPage = projectRepository.findAllByPoIdAndProjectNameContaining(poId, projectName, paging);
                    for (Project project : projectPage) {
                        Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
                        Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
                        Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());
                        Set<ProjectPersonasDTO> projectPersonasResponseDTOS = new HashSet<>();

                        projectPersonas.forEach(projectPersonasItem -> {
                            ProjectPersonasDTO personasDTO;
                            Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
                            Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();
                            Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

                            projectTechnicals.forEach(technical -> {
                                Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                                technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
                            });
                            personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

                            projectPersonasResponseDTOS.add(personasDTO);
                        });

                        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
                        Set<Document> documents = documentRepository.findAllByProjectId(project.getId());
                        projects.add(ProjectGetAllConverter.toProjectCreationDTO(project, tools, processes, projectPersonasResponseDTOS,
                                projectMembers, documents));
                    }
                    totalPages = projectPage.getTotalPages();
                    response.put("project", projects);
                    response.put("totalPages", totalPages);
                }
            } else {
                if (poId == null) {
                    List<Project> projectList = projectRepository.findAll();
                    for (Project project : projectList) {
                        Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
                        Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
                        Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());

                        Set<ProjectPersonasDTO> projectPersonasResponseDTOS = new HashSet<>();

                        projectPersonas.forEach(projectPersonasItem -> {
                            ProjectPersonasDTO personasDTO;
                            Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
                            Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();
                            Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

                            projectTechnicals.forEach(technical -> {
                                Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                                technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
                            });
                            personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

                            projectPersonasResponseDTOS.add(personasDTO);
                        });
                        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
                        Set<Document> documents = documentRepository.findAllByProjectId(project.getId());
                        projects.add(ProjectGetAllConverter.toProjectCreationDTO(project, tools, processes, projectPersonasResponseDTOS,
                                projectMembers, documents));
                    }

                    response.put("project", projects);
                } else {
                    List<Project> projectList;
                    if (StringUtils.isNotEmpty(projectName)) {
                        projectList = projectRepository.findAllByPoIdAndProjectNameContaining(poId, projectName);
                    } else {
                        projectList = projectRepository.findAllByPoId(poId);
                    }

                    for (Project project : projectList) {
                        Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
                        Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
                        Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());


                        Set<ProjectPersonasDTO> projectPersonasResponseDTOS = new HashSet<>();

                        projectPersonas.forEach(projectPersonasItem -> {
                            ProjectPersonasDTO personasDTO;
                            Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
                            Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();

                            Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

                            projectTechnicals.forEach(technical -> {
                                Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                                technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
                            });
                            personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

                            projectPersonasResponseDTOS.add(personasDTO);
                        });


                        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
                        Set<Document> documents = documentRepository.findAllByProjectId(project.getId());
                        projects.add(ProjectGetAllConverter.toProjectCreationDTO(project, tools, processes, projectPersonasResponseDTOS,
                                projectMembers, documents));
                    }
                    response.put("project", projects);
                }
            }


            if (projects.isEmpty()) {
                return null;
            } else {
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProjectResponseDTO getProject(Integer projectId) {
        try {
            Optional<Project> projectOptional = projectRepository.findById(projectId);

            if (projectOptional.isEmpty()) {
                return null;
            }

            Project project = projectOptional.get();

            Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
            Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
            Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());


            Set<ProjectPersonasDTO> projectPersonasDTOS = new HashSet<>();

            projectPersonas.forEach(projectPersonasItem -> {
                Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
                Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();

                Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

                projectTechnicals.forEach(technical -> {
                    Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                    technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
                });
                ProjectPersonasDTO personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

                projectPersonasDTOS.add(personasDTO);
            });


            Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
            Set<Document> documents = documentRepository.findAllByProjectId(project.getId());

            ProjectResponseDTO response =
                    ProjectGetAllConverter.toProjectCreationDTO(
                            project,
                            tools,
                            processes,
                            projectPersonasDTOS,
                            projectMembers,
                            documents
                    );

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    public List<MatchingScore> getMatchingScore(Integer projectId, Integer positionId, String positionName) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Project ID Invalid"));

        String projectName = project.getProjectName();

        String levelNeed = "Junior";

        ProjectPersonas projectPersonas = projectPersonasRepository
                .findByProjectIdAndPositionId(projectId, positionId).get();

        if (projectPersonas == null) throw new NotFoundException("Please update persona's capabilities to see this");

        Set<PersonasTechnical> allByProjectPersonasId = personasTechnicalRepository
                .findAllByProjectPersonasId(projectPersonas.getId())
                .orElseThrow(() -> new NotFoundException("Please update technical of persona to see this."));

        List<String> skills = new ArrayList<>();
        allByProjectPersonasId.forEach(
                personasTechnical -> skills.add(
                        capabilytiesService.getCapabilityByid(personasTechnical.getCapabilitiesId()).getName()));
        String skillNeed = String.join(", ", skills);

        GetMatchingScoreDTO requestBody = new GetMatchingScoreDTO(
                projectName,
                positionName,
                levelNeed,
                skillNeed
        );

        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<GetMatchingScoreDTO> requestEntity = new HttpEntity<>(requestBody);
            String recommendationHost = "http://localhost:7777/";

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    recommendationHost, HttpMethod.GET, requestEntity, String.class);

            String body = responseEntity.getBody();
            JSONArray jsonArray = new JSONArray(body);
            List<MatchingScore> matchingScoreList = new ArrayList<>();

            for (Object matchingScore : jsonArray) {
                ObjectMapper objectMapper = new ObjectMapper();
                MatchingScore valueMapper = objectMapper.readValue(matchingScore.toString(), MatchingScore.class);
                matchingScoreList.add(valueMapper);
            }

            log.warn("Success, Response: {}", matchingScoreList);
            return matchingScoreList;
        } catch (Exception e) {
            log.warn("From Project-sv, call Recommendation failed. GetMatchingScore: {}", e.getMessage());
        }
        return null;
    }

    private ProjectResponseDTO projectResponseDetail(Project project) {
        Set<Tool> tools = projectToolService.getToolByProjectId(project.getId());
        Set<Process> processes = projectProcessService.getProcessByProjectId(project.getId());
        Set<ProjectPersonas> projectPersonas = projectPersonasRepository.findAllByProjectId(project.getId());

        Set<ProjectPersonasDTO> projectPersonasResponseDTOS = new HashSet<>();

        projectPersonas.forEach(projectPersonasItem -> {
            ProjectPersonasDTO personasDTO;
            Set<PersonasTechnical> projectTechnicals = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasItem.getId()).get();
            Set<PersonasTechnicalDTO> technicalDTOS = new HashSet<>();
            Position position = positionService.getPositionById(projectPersonasItem.getPositionId());

            projectTechnicals.forEach(technical -> {
                Capabilities capabilities = capabilytiesService.getCapabilityByid(technical.getCapabilitiesId());

                technicalDTOS.add(PersonasTechnicalConvertor.toDTO(technical, capabilities));
            });
            personasDTO = PersonaConverter.toPersonaDTO(projectPersonasItem, position, technicalDTOS);

            projectPersonasResponseDTOS.add(personasDTO);
        });
        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(project.getId());
        Set<Document> documents = documentRepository.findAllByProjectId(project.getId());
        return ProjectGetAllConverter.toProjectCreationDTO(project, tools, processes, projectPersonasResponseDTOS,
                projectMembers, documents);
    }

    @Override
    public Map<Integer, Object> getMapProjects(List<Integer> projectIds) {
        Map<Integer, Object> result = new HashMap<>();

        List<Project> projects = projectRepository.findByIdIn(projectIds);
        projects.forEach(pr -> {
            result.put(pr.getId(), projectResponseDetail(pr));
        });

        return result;
    }

    @Override
    public ProjectSkillsDTO getProjectSkills(Integer projectId) {
        Project project = projectRepository.getById(projectId);
        if (project != null) {
            ProjectSkillsDTO projectSkills = new ProjectSkillsDTO();
            projectSkills.setProjectId(project.getId());
            projectSkills.setProjectName(project.getProjectName());
            Set<Integer> skills = new HashSet<>();
            Set<ProjectPersonas> ppl = projectPersonasRepository.findAllByProjectId(project.getId());
            if (!CollectionUtils.isEmpty(ppl)) {
                List<PersonasTechnical> ptl = personasTechnicalRepository.findAllByProjectPersonasIdIn(ppl.stream().map(pt -> pt.getId()).collect(Collectors.toList()));
                if (!CollectionUtils.isEmpty(ptl)) {
                    ptl.forEach(pt -> {
                        skills.add(pt.getCapabilitiesId());
                    });
                }
            }

            projectSkills.setCapabilitiesId(skills);

            return projectSkills;
        }

        return null;
    }

    @Override
    public List<ProjectResponseDTO> getCollaborateProjects(Integer userId, CollaborateStatus collaborateStatus) {
        List<ProjectResponseDTO> projects = new ArrayList<>();
        List<ProjectUserStatus> projectUserStatuses = projectUserStatusRepository.findAllByUserIdAndCollaborateStatus(userId, collaborateStatus);

        projectUserStatuses.forEach(e -> {
            Optional<Project> project = projectRepository.findById(e.getProjectId());
            project.ifPresent(value -> projects.add(projectResponseDetail(value)));
        });

        return projects;
    }

    @Override
    public List<ProjectResponseDTO> getInterestedProjects(Integer userId, InterestingStatus interestingStatus) {
        List<ProjectResponseDTO> projects = new ArrayList<>();
        List<ProjectUserStatus> projectUserStatuses = projectUserStatusRepository.findAllByUserIdAndInterestingStatus(userId, interestingStatus);

        projectUserStatuses.forEach(e -> {
            Optional<Project> project = projectRepository.findById(e.getProjectId());
            project.ifPresent(value -> projects.add(projectResponseDetail(value)));
        });

        return projects;
    }

    public List<MatchingSkill> getMatchingSkillList(Set<PersonasTechnical> personasTechnicals) {
        List<MatchingSkill> matchingSkills = new ArrayList<>();
        personasTechnicals.forEach(personasTechnical -> {
            MatchingSkill matchingSkill = new MatchingSkill(
                    personasTechnical.getCapabilitiesId(),
                    personasTechnical.getLevel()
            );
            matchingSkills.add(matchingSkill);
        });
        return matchingSkills;
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public Integer createProject(ProjectInfoDTO projectInfoDTO, Integer poId, MultipartFile imageFile) {
        Project project = ProjectGetAllConverter.toEntity(projectInfoDTO);
        project.setUpdatedAt(LocalDateTime.now());
        project.setPoId(poId);
        try {

            project = projectRepository.save(project);
            Integer projectId = project.getId();
            fileStorageService.initProjectFolderByProjectId(projectId);
            if (imageFile != null) {
                project.setImgPath(saveProjectImageService(projectId, imageFile));
            }
            return projectId;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    public String saveProjectImageService(Integer projectId, MultipartFile file) {
        fileStorageService.deleteProjectImage(projectId);
        return fileStorageService.saveProjectImage(projectId, file);
    }

    @Override
    public Optional<List<String>> getAllProjectNames() {
        try {
            List<Project> all = projectRepository.findAll();
            List<String> projectNames = new ArrayList<>();
            all.forEach(project -> projectNames.add(project.getProjectName()));

            return Optional.of(projectNames);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("while get all ProjectNames");
            return Optional.empty();
        }
    }
}
