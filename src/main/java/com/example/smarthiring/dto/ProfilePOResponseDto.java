package com.smartdev.iresource.personal.dto;

import com.smartdev.iresource.personal.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfilePOResponseDto {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String division;
    private String contractualTerm;
    private List<Position> positions;
}
