package com.example.smarthiring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageProjectMatchingDto {
    @JsonProperty("projects")
    private List<ProjectMatchingResponseDto> projectMatchingReponseDtos;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
}
