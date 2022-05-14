package com.smartdev.iresource.personal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String position;
    private String contractualTerm;
    private Integer divisionId;
    private Integer locationId;
}
