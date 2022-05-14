package com.smartdev.iresource.project.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.smartdev.iresource.project.common.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectInfoDTO {

    String imgPath;

    @NotBlank
    private String projectName;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @NotBlank
    private String problemStatement;

    @NotBlank
    private String bigVision;

    @NotBlank
    private String valueProposition;

    @NotBlank
    private String customer;

    private String revenueStreams;

    @NotNull
    private Double projectCompletion;

    @NotNull
    private String startAt;

    @NotNull
    private String endAt;

    private Integer poId;
}
