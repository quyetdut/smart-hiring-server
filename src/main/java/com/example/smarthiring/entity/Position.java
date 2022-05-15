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
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull(message = "name is required")
    @Column(unique = true)
    private String name ;

    @NotNull(message = "icon is required")
    private String icon ;

    @NotNull(message = "not null")
    private String roleName;
}
