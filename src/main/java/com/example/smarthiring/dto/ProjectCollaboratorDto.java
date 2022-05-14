package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCollaboratorDto {
    private Integer userId;
    private String userName;
    private String positionName;
}
