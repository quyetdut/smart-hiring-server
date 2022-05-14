package com.smartdev.iresource.personal.entity;

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
public class ProfileCapabilities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;

    @NotNull(message = "icon is required")
    private Integer profileId ;

    @NotNull(message = "icon is required")
    private Integer capabilitiesId ;

    @NotNull
    private Integer level;

}
