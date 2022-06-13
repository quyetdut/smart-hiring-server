package com.example.smarthiring.common.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@Slf4j
@Component
public class ScheduleDeleteRecordBatchJobs {

    @Autowired
    DataSource dataSource;

    @Scheduled(fixedRate = 1000)
    public void jdbcTemplate() {
//        try {
//            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//            jdbcTemplate.execute("delete from BATCH_STEP_EXECUTION_SEQ;");
//            jdbcTemplate.execute("delete from BATCH_STEP_EXECUTION_CONTEXT;");
//            jdbcTemplate.execute("delete from BATCH_STEP_EXECUTION;");
//            jdbcTemplate.execute("delete from BATCH_JOB_SEQ;");
//            jdbcTemplate.execute("delete from BATCH_JOB_EXECUTION_SEQ;");
//            jdbcTemplate.execute("delete from BATCH_JOB_EXECUTION_PARAMS;");
//            jdbcTemplate.execute("delete from BATCH_JOB_EXECUTION_CONTEXT;");
//            jdbcTemplate.execute("delete from BATCH_JOB_EXECUTION;");
//            jdbcTemplate.execute("delete from BATCH_JOB_INSTANCE;");
//            log.info("xoa ok roi nha");
//        } catch (Exception ex) {
//            log.error("loi ti, binh tinh nao");
//        }

    }
}
