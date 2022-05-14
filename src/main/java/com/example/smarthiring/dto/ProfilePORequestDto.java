package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePORequestDto {

    private Integer userId;
    private String firstName;
    private String lastName;
    private String contractualTerm;
    private String division;
    private Set<String> positions;
}
