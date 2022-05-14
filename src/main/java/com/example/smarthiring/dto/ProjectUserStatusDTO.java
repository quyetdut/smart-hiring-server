package com.smartdev.iresource.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartdev.iresource.project.common.enums.CollaborateStatus;
import com.smartdev.iresource.project.common.enums.InterestingStatus;
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
