package com.example.smarthiring.dto;

import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserStatusDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private CollaborateStatus collaborateStatus;
    private Integer projectId;
    private Integer userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private InterestingStatus interestingStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer positionId;
}
