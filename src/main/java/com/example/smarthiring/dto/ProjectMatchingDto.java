package com.smartdev.iresource.personal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMatchingDto {
    private Integer projectId;
    private Integer userId;
    private Integer matchingScore;
}
