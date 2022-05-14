package com.smartdev.iresource.project.entity;

import com.smartdev.iresource.project.common.enums.CollaborateStatus;
import com.smartdev.iresource.project.common.enums.InterestingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "project_user_status")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectUserStatus extends StandardEntity {
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "position_id")
    private Integer positionId;

    @Enumerated(EnumType.STRING)
    private CollaborateStatus collaborateStatus;

    @Enumerated(EnumType.STRING)
    private InterestingStatus interestingStatus;
}
