package com.smartdev.iresource.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity(name = "project_member")
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMember extends StandardEntity {
    @Column(name = "project_id")
    private Integer projectId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "position_id")
    private Integer positionId;
}
