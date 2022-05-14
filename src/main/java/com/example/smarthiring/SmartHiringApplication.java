package com.example.smarthiring;

import com.example.smarthiring.service.implement.FileServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@SpringBootApplication
@Slf4j
public class SmartHiringApplication implements CommandLineRunner {
//    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

    @Resource
    FileServiceImpl storageService;
    public static void main(String[] args) {
        SpringApplication.run(SmartHiringApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            storageService.init();
        } catch (Exception e) {
            log.warn("failed to initialize project folder " + e.getClass());
        }
    }
}
