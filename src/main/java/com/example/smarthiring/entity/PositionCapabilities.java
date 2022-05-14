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
public class PositionCapabilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull(message = "position id is required")
    private Integer positionId ;

    @NotNull(message = "capabilities id is required")
    private Integer capabilitiesId ;
}
