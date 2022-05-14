package com.example.smarthiring.entity;

import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
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
