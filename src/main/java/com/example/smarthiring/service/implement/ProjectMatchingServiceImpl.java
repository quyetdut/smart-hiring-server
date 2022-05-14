package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.*;
import com.example.smarthiring.entity.Position;
import com.example.smarthiring.entity.Profiles;
import com.example.smarthiring.entity.ProjectMatching;
import com.example.smarthiring.enums.ExceptionDefinition;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.repository.PositionRepository;
import com.example.smarthiring.repository.ProfileRepository;
import com.example.smarthiring.repository.ProjectMatchingRepository;
import com.example.smarthiring.service.CapabilytiesService;
import com.example.smarthiring.service.ProjectMatchingService;
import com.example.smarthiring.service.ProjectService;
import com.example.smarthiring.service.ServiceUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectMatchingServiceImpl implements ProjectMatchingService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProjectMatchingRepository projectMatchingRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private CapabilytiesService capabilytiesService;

    @Autowired
    private ServiceUtil serviceUtil;

    @Autowired
    private ProjectService projectService;

    private static final String MATCHING_SCORE = "matchingScore";

    @Override
    public boolean saveMatchingScore(ProjectMatchingDto projectMatchingDto) {
        Profiles profile = profileRepository.findByUserId(projectMatchingDto.getUserId()).orElseThrow(() -> new NotFoundException(ExceptionDefinition.PROFILE_NOT_FOUND.getMessage(), ExceptionDefinition.PROFILE_NOT_FOUND.getErrorCode()));

        Object object = projectService.getProjectSkills(projectMatchingDto.getProjectId());

        try {
            ProjectMatching projectMatching = projectMatchingRepository.findByProjectIdAndProfileId(projectMatchingDto.getProjectId(), profile.getId()).orElse(null);
            if (projectMatching == null) {
                projectMatching = new ProjectMatching();
                projectMatching.setProjectId(projectMatchingDto.getProjectId());
                projectMatching.setProfileId(profile.getId());
            }
            if (object != null) {
                ObjectMapper oMapper = new ObjectMapper();
                String json = oMapper.writeValueAsString(object);
                ProjectSkillsDto projectSkillsDto = oMapper.readValue(json, ProjectSkillsDto.class);
                projectMatching.setProjectName(projectSkillsDto.getProjectName());
                if (CollectionUtils.isNotEmpty(projectSkillsDto.getCapabilitiesId())) {
                    projectMatching.setSkills(capabilytiesService.getCapabilityNames(projectSkillsDto.getCapabilitiesId()));
                }
            }

            projectMatching.setMatchingScore(projectMatchingDto.getMatchingScore());
            projectMatchingRepository.save(projectMatching);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public PageProjectMatchingDto getProjectMatchingForUser(Integer userId, String filterValue, Integer page, Integer size) {
        Profiles profile = profileRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ExceptionDefinition.PROFILE_NOT_FOUND.getMessage(), ExceptionDefinition.PROFILE_NOT_FOUND.getErrorCode()));
        Pageable pageable = PageRequest.of(page, size, Sort.by(MATCHING_SCORE).descending());
        Page<ProjectMatching> projectMatchingPage;
        if (StringUtils.isNotBlank(filterValue)) {
            projectMatchingPage = projectMatchingRepository.findAllByProfileIdAndProjectNameContainsOrProfileIdAndSkillsContains(profile.getId(), filterValue, profile.getId(), filterValue, pageable);
        } else {
            projectMatchingPage = projectMatchingRepository.findAllByProfileId(profile.getId(), pageable);
        }
        if (projectMatchingPage != null) {
            PageProjectMatchingDto pageProjectMatchingDto = new PageProjectMatchingDto();
            List<ProjectMatchingResponseDto> projectMatchingResponseDtos = new ArrayList<>();

            Map<Integer, Object> mapProjects = projectService.getMapProjects((projectMatchingPage.getContent().stream().map(pm -> pm.getProjectId()).collect(Collectors.toList())));
            projectMatchingPage.getContent().forEach(pj -> {
                ProjectMatchingResponseDto dto = new ProjectMatchingResponseDto();
                dto.setMatchingScore(pj.getMatchingScore());
                dto.setProject(mapProjects.get(pj.getProjectId()));

                PositionRequestDto positionRequestDto = new PositionRequestDto();
                positionRequestDto.setPositionId(pj.getPositionId());
                Position position = positionRepository.getById(pj.getPositionId());
                if (position != null) {
                    positionRequestDto.setName(position.getName());
                    positionRequestDto.setIcon(position.getIcon());
                }
                dto.setPositionRequestDto(positionRequestDto);

                projectMatchingResponseDtos.add(dto);
            });

            pageProjectMatchingDto.setProjectMatchingReponseDtos(projectMatchingResponseDtos);
            pageProjectMatchingDto.setCurrentPage(projectMatchingPage.getNumber());
            pageProjectMatchingDto.setTotalItems(projectMatchingPage.getTotalElements());
            pageProjectMatchingDto.setTotalPages(projectMatchingPage.getTotalPages());
            return pageProjectMatchingDto;
        } else {
            return null;
        }
    }

    @Override
    public ProjectMatchingResponseDto getMatchingScore(Integer userId, Integer projectId) {
        Profiles profile = profileRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException(ExceptionDefinition.PROFILE_NOT_FOUND.getMessage(), ExceptionDefinition.PROFILE_NOT_FOUND.getErrorCode()));
        Optional<ProjectMatching> projectMatching = projectMatchingRepository.findByProjectIdAndProfileId(projectId, profile.getId());
        ProjectMatchingResponseDto result = new ProjectMatchingResponseDto();
        if (projectMatching.isPresent()) {
            result.setMatchingScore(projectMatching.get().getMatchingScore());
        }

        return result;
    }

    @Override
    public Integer getCountMatching(Integer projectId, Integer positionId) {
        List<ProjectMatching> projectMatchings = projectMatchingRepository.findAllByProjectIdAndPositionId(projectId, positionId);

        if (CollectionUtils.isNotEmpty(projectMatchings)) {
            return projectMatchings.size();
        } else {
            return 0;
        }
    }

    @Override
    public Boolean deleteProjectMatching(Integer projectId, Integer userId) {
        try {
            Optional<Profiles> profilesOptional = profileRepository.findByUserId(userId);
            if (!profilesOptional.isPresent()) return true;
            Optional<ProjectMatching> projectMatchingOptional = projectMatchingRepository.findByProjectIdAndProfileId(
                    projectId, profilesOptional.get().getId());
            if (!projectMatchingOptional.isPresent()) return true;
            projectMatchingRepository.deleteById(projectMatchingOptional.get().getId());

            return true;
        } catch (Exception ex) {
            log.info(ex.getMessage());
            return false;
        }
    }
}
