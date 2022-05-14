package com.example.smarthiring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperiences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull(message = "startAt is required")
    private LocalDate startAt;

    @NotNull(message = "endAt is required")
    private LocalDate endAt;

    @NotNull(message = "description is required")
    @Column(length = 100000)
    private String description;

    @NotNull(message = "employer is required")
    private String employer;

    @NotNull(message = "businessType is required")
    private String businessType;

    @NotNull(message = "position is required")
    private String position;

    @NotNull(message = "profileId is required")
    private Integer profileId ;
}
