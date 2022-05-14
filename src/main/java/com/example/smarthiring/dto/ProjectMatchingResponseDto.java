package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMatchingResponseDto {
    private Integer matchingScore;

    @JsonProperty("position")
    private PositionRequestDto positionRequestDto;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object project;
}
