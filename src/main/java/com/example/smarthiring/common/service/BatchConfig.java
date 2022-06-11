package com.example.smarthiring.common.service;

import com.example.smarthiring.common.Common;
import com.example.smarthiring.common.vo.UserSkills;
import com.example.smarthiring.entity.ProjectMatching;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    @Autowired
    public JobBuilderFactory jobs;

    @Autowired
    public StepBuilderFactory steps;

    @Autowired
    ScoreMatchReader scoreMatchReader;

    @Autowired
    ScoreMatchProcess scoreMatchProcess;

    @Autowired
    ScoreMatchWriter scoreMatchWriter;

    @Bean
    public Step scoreMatchStep() {
        return steps.get("scoreMatchStep")
                .<UserSkills, List<ProjectMatching>> chunk(1000)
                .reader(scoreMatchReader)
                .processor(scoreMatchProcess)
                .writer(scoreMatchWriter)
                .build();
    }

    @Bean(name= Common.SCORE_MATCH_JOB_NAME)
    public Job scoreMatchJob() {
        return jobs.get(Common.SCORE_MATCH_JOB_NAME).incrementer(new RunIdIncrementer()).flow(scoreMatchStep()).end().build();
    }
}

