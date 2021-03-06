package com.example.smarthiring.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAwards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "icon is required")
    private Integer profileId ;

    @NotNull(message = "icon is required")
    private Integer awardId ;

    @Column(name = "amount",columnDefinition = "integer default 0")
    private Integer amount;
}
