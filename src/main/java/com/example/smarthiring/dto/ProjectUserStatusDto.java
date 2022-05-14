package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserStatusDto {
    private Integer projectId;
    private Integer userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer positionId;
}
