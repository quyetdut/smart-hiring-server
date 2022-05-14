package com.example.smarthiring.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPersonas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numberNeeded;
    private Integer numberCurrent;
    private Integer monthNeeded;
    private Double utilization;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt;

    @Column(name = "project_id")
    private Integer projectId;

    private Integer positionId;

    public ProjectPersonas(Integer id, Integer numberNeeded, Integer numberCurrent, Integer monthNeeded, Double utilization, Integer positionId) {
        this.id = id;
        this.numberNeeded = numberNeeded;
        this.numberCurrent = numberCurrent;
        this.createdAt = LocalDateTime.now();
        this.monthNeeded = monthNeeded;
        this.utilization = utilization;
        this.positionId = positionId;
    }
}
