package com.example.smarthiring.service.implement;

import com.example.smarthiring.common.ResponseMessage;
import com.example.smarthiring.converter.PersonasTechnicalConvertor;
import com.example.smarthiring.converter.ProjectPersonasConverter;
import com.example.smarthiring.dto.PersonasTechnicalDTO;
import com.example.smarthiring.dto.ProjectPersonasDTO;
import com.example.smarthiring.entity.Capabilities;
import com.example.smarthiring.entity.PersonasTechnical;
import com.example.smarthiring.entity.Position;
import com.example.smarthiring.entity.ProjectPersonas;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.exception.RequestFailedException;
import com.example.smarthiring.exception.SomethingWrongException;
import com.example.smarthiring.repository.*;
import com.example.smarthiring.service.ProjectPersonasService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectPersonasServiceImpl implements ProjectPersonasService, ResponseMessage {

    private final ProjectPersonasRepository projectPersonasRepository;
    private final ProjectRepository projectRepository;
    private final PersonasTechnicalRepository personasTechnicalRepository;
    private final PositionRepository positionRepository;

    private final CapabilitiesRepository capabilitiesRepository;


    @Override
    public Boolean updateNumberCurrentProjectPersonas(ProjectPersonasDTO personasDTO) {
        try {
            Optional<ProjectPersonas> personasOptional = projectPersonasRepository.findById(personasDTO.getId());
            ProjectPersonas projectPersonas;

            if (personasOptional.isEmpty()) {
                throw new NotFoundException("i");
            }

            projectPersonas = personasOptional.get();

            if (projectPersonas.getMonthNeeded() < personasDTO.getNumberCurrent()) {
                throw new RequestFailedException("");
            }

            projectPersonas.setNumberCurrent(personasDTO.getNumberCurrent());
            projectPersonas.setUpdatedAt(LocalDateTime.now());
            projectPersonasRepository.save(projectPersonas);

            return true;
        } catch (NotFoundException e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, projectPersonas is not valid");
            throw new NotFoundException(NOT_FOUND);
        } catch (RequestFailedException e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, numberCurrent greater than numberNeeded");
            throw new RequestFailedException(REQUEST_FAILED);
        } catch (Exception e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, Unknown!");
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProjectPersonasDTO updateProjectPersonasWithProjectId(ProjectPersonasDTO projectPersonasDTO, Integer projectId) {
        try {
            if (!projectRepository.existsById(projectId)
                    || !positionRepository.existsById(projectPersonasDTO.getPositionId())) {
                throw new NotFoundException("");
            }

            Optional<ProjectPersonas> projectPersonas =
                    projectPersonasRepository
                            .findByProjectIdAndPositionId(projectId, projectPersonasDTO.getPositionId());


            projectPersonasDTO.setProjectId(projectId);

            ProjectPersonasDTO projectPersonasResponse = null;

            if (projectPersonas.isPresent()) {
                projectPersonasResponse = updateProjectPersonas(projectPersonasDTO);
            } else {
                projectPersonasResponse = saveProjectPersonas(projectPersonasDTO);
            }

            return projectPersonasResponse;
        } catch (NotFoundException e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, Not Found");
            throw new NotFoundException(NOT_FOUND);
        } catch (RequestFailedException e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, numberCurrent greater than numberNeeded");
            throw new RequestFailedException(REQUEST_FAILED);
        } catch (Exception e) {
            log.error("updateProjectPersonas method in ProjectPersonasService failed, Unknown!");
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }


    }

    @Override
    public List<Integer> getCapabilitiesId(Integer projectId, Integer positionId) {
        List<Integer> capabilitesId = new ArrayList<>();
        Optional<ProjectPersonas> projectPersonasOptional = projectPersonasRepository.findByProjectIdAndPositionId(projectId, positionId);
        if (projectPersonasOptional.isPresent()) {
            Optional<Set<PersonasTechnical>> personasTechnicalsOptional = personasTechnicalRepository.findAllByProjectPersonasId(projectPersonasOptional.get().getId());
            if (personasTechnicalsOptional.isPresent()) {
                capabilitesId = personasTechnicalsOptional.get().stream().map(pt -> pt.getCapabilitiesId()).collect(Collectors.toList());
            }
        }

        return capabilitesId;
    }

    @Transactional(
            propagation = Propagation.REQUIRED,
            rollbackFor = {
                    NotFoundException.class,
                    RuntimeException.class
            }
    )
    ProjectPersonasDTO saveProjectPersonas(ProjectPersonasDTO projectPersonasDTO) {
        //check exit projectPersonas
        if (!positionRepository.existsById(projectPersonasDTO.getPositionId())) {
            throw new NotFoundException(NOT_FOUND);
        }

        ProjectPersonas projectPersonas = ProjectPersonasConverter.toEntity(projectPersonasDTO);

        ProjectPersonas finalProjectPersonas = projectPersonasRepository.save(projectPersonas);

        Position position =
                positionRepository.getById(finalProjectPersonas.getPositionId());

        ProjectPersonasDTO projectPersonasResponse = null;
        List<PersonasTechnical> personasTechnicals = new ArrayList<>();

        Set<PersonasTechnicalDTO> technicalDTOS =
                projectPersonasDTO
                        .getPersonasTechnicals();
        Set<PersonasTechnical> technicalSet = new HashSet<>();

        technicalDTOS.forEach(technicalItem -> {
            PersonasTechnical technical = null;

            if (capabilitiesRepository.existsById(technicalItem.getCapabilitiesId())) {
                technical = PersonasTechnicalConvertor.toEntity(technicalItem);
                technical.setProjectPersonasId(finalProjectPersonas.getId());

                technicalSet.add(technical);
            }
        });

        if (!technicalSet.isEmpty()) {
            personasTechnicals = personasTechnicalRepository.saveAll(technicalSet);
        }

        Set<PersonasTechnicalDTO> personasTechnicalDTOS = new HashSet<>();

        personasTechnicals.forEach(personasTechnical -> {
            PersonasTechnicalDTO personasTechnicalDTO = new PersonasTechnicalDTO();

            Capabilities capabilities = capabilitiesRepository.getById(personasTechnical.getCapabilitiesId());

            personasTechnicalDTO = PersonasTechnicalConvertor.toDTO(personasTechnical, capabilities);

            personasTechnicalDTOS.add(personasTechnicalDTO);
        });


        projectPersonasResponse =
                ProjectPersonasConverter.toDTO(
                        finalProjectPersonas,
                        position,
                        personasTechnicalDTOS
                );

        return projectPersonasResponse;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = RuntimeException.class)
    ProjectPersonasDTO updateProjectPersonas(ProjectPersonasDTO projectPersonasDTO) {
        try {
            Optional<ProjectPersonas> projectPersonasOptional =
                    projectPersonasRepository
                            .findByProjectIdAndPositionId(
                                    projectPersonasDTO.getProjectId(),
                                    projectPersonasDTO.getPositionId()
                            );

            ProjectPersonas projectPersonas =
                    ProjectPersonasConverter
                            .toEntity(projectPersonasDTO);
            projectPersonas.setId(projectPersonasOptional.get().getId());

            Set<PersonasTechnicalDTO> technicalDTOS =
                    projectPersonasDTO
                            .getPersonasTechnicals();
            Set<PersonasTechnical> technicalSet = new HashSet<>();

            technicalDTOS.forEach(technicalItem -> {
                PersonasTechnical technical = null;
                if (capabilitiesRepository.existsById(technicalItem.getCapabilitiesId())) {
                    technical = PersonasTechnicalConvertor.toEntity(technicalItem);
                    technical.setProjectPersonasId(projectPersonas.getId());
                    technicalSet.add(technical);
                }
            });

            ProjectPersonas projectPersonasData = projectPersonasRepository.save(projectPersonas);

            Position position =
                    positionRepository.getById(projectPersonasData.getPositionId());

            ProjectPersonasDTO projectPersonasResponse = null;

            personasTechnicalRepository.deleteAllByProjectPersonasId(projectPersonas.getId());
            List<PersonasTechnical> personasTechnicals = new ArrayList<>();
            Set<PersonasTechnicalDTO> personasTechnicalDTOS = new HashSet<>();

            if (!technicalSet.isEmpty()) {
                personasTechnicals = personasTechnicalRepository.saveAll(technicalSet);
            }

            personasTechnicals.forEach(personasTechnical -> {
                PersonasTechnicalDTO personasTechnicalDTO = new PersonasTechnicalDTO();

                Capabilities capabilities =
                        capabilitiesRepository.getById(personasTechnical.getCapabilitiesId());

                personasTechnicalDTO = PersonasTechnicalConvertor.toDTO(personasTechnical, capabilities);

                personasTechnicalDTOS.add(personasTechnicalDTO);
            });

            projectPersonasResponse =
                    ProjectPersonasConverter.toDTO(
                            projectPersonasData,
                            position,
                            personasTechnicalDTOS
                    );

            return projectPersonasResponse;
        } catch (RuntimeException e) {
            log.error(
                    "updateProjectPersonas method in ProjectPersonasService, with Message: "
                            + e.getMessage()
            );
            throw new RuntimeException(e.getMessage());
        }
    }

}
