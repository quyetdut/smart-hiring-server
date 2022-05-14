package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PositionRequestDto {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer positionId;
    private String name;
    private String icon;
}
