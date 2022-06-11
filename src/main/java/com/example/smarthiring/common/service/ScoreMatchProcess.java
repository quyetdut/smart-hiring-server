package com.example.smarthiring.common.service;

import com.example.smarthiring.common.vo.UserSkills;
import com.example.smarthiring.dto.CapabilityLevelDto;
import com.example.smarthiring.entity.*;
import com.example.smarthiring.repository.CapabilitiesRepository;
import com.example.smarthiring.repository.PersonasTechnicalRepository;
import com.example.smarthiring.repository.ProjectPersonasRepository;
import com.example.smarthiring.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScoreMatchProcess implements ItemProcessor<UserSkills, List<ProjectMatching>> {

    @Autowired
    private PersonasTechnicalRepository personasTechnicalRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectPersonasRepository projectPersonasRepository;

    @Autowired
    private CapabilitiesRepository capabilitiesRepository;

    @Override
    public List<ProjectMatching> process(UserSkills userSkills) throws Exception {
        List<Integer> capabilitiesIdList = userSkills.getCapabilityLevelDtos().stream().map(us -> us.getCapabilityId()).collect(Collectors.toList());
        Set<PersonasTechnical> personasTechnicals = personasTechnicalRepository.findAllByCapabilitiesIdIn(capabilitiesIdList);
        List<Integer> projectPersonasIdList = personasTechnicals.stream().map(pt -> pt.getProjectPersonasId()).collect(Collectors.toList());
        Map<Integer, List<PersonasTechnical>> personasTechnicalByPersonas = personasTechnicalRepository
                .findAllByProjectPersonasIdIn(projectPersonasIdList)
                .stream().collect(Collectors.groupingBy(PersonasTechnical::getProjectPersonasId));

        Set<ProjectPersonas> projectPersonasList = projectPersonasRepository.findAllByIdIn(projectPersonasIdList);
        List<Integer> projectIdList = projectPersonasList.stream().map(pp -> pp.getProjectId()).collect(Collectors.toList());
        Map<Integer, Project> projectMaps = projectRepository.findAllByIdIn(projectIdList).stream().collect(Collectors.toMap(Project::getId, Function.identity()));
        List<ProjectMatching> projectMatchings = new ArrayList<>();

        for (ProjectPersonas pp : projectPersonasList) {
            ProjectMatching projectMatching = new ProjectMatching();
            Project project = projectMaps.get(pp.getProjectId());
            projectMatching.setProjectId(project.getId());
            projectMatching.setProjectName(project.getProjectName());
            projectMatching.setProfileId(userSkills.getProfileId());
            projectMatching.setPositionId(pp.getPositionId());
            List<Capabilities> capabilities = capabilitiesRepository.findAllByIdIn(personasTechnicalByPersonas.get(pp.getId()).stream().map(ptbp -> ptbp.getCapabilitiesId()).collect(Collectors.toList()));
            projectMatching.setSkills(String.join(",", capabilities.stream().map(c -> c.getName()).collect(Collectors.toList())));

            List<CapabilityLevelDto> personaSkills = convertPersonalTechnical(personasTechnicalByPersonas.get(pp.getId()));
            Map<Integer, Double> skillPercent = RecommendService.getInstance().getPercentPersonaSkills(personaSkills);
            projectMatching.setMatchingScore((int) RecommendService.getInstance().getMatchingScore(userSkills.getCapabilityLevelDtos(), personaSkills, skillPercent));

            projectMatchings.add(projectMatching);
        }

        return projectMatchings;
    }

    private List<CapabilityLevelDto> convertPersonalTechnical(List<PersonasTechnical> ptList) {
        List<CapabilityLevelDto> capabilityLevelDtos = new ArrayList<>();
        ptList.forEach(pt -> {
            CapabilityLevelDto capabilityLevelDto = new CapabilityLevelDto();
            capabilityLevelDto.setCapabilityId(pt.getCapabilitiesId());
            capabilityLevelDto.setLevel(pt.getLevel());
            capabilityLevelDto.setImportance(pt.getImportance());
            capabilityLevelDtos.add(capabilityLevelDto);
        });

        return capabilityLevelDtos;
    }
}
