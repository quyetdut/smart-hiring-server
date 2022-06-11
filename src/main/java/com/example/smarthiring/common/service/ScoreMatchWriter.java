package com.example.smarthiring.common.service;

import com.example.smarthiring.entity.ProjectMatching;
import com.example.smarthiring.repository.ProjectMatchingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScoreMatchWriter implements ItemWriter<List<ProjectMatching>> {

    @Autowired
    ProjectMatchingRepository projectMatchingRepository;


    public void write(List<? extends List<ProjectMatching>> projectMatchingResults) {
        for (List<ProjectMatching> projectMatchings : projectMatchingResults) {
            List<ProjectMatching> projectMatchingsRemove = projectMatchingRepository.findAllByProfileIdIn(projectMatchings.stream().map(pm -> pm.getProfileId()).collect(Collectors.toSet()));
            projectMatchingRepository.deleteAll(projectMatchingsRemove);
            projectMatchingRepository.saveAll(projectMatchings);
        }
    }
}
