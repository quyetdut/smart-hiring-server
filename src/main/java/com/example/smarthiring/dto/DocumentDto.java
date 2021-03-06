package com.example.smarthiring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentDto {
    private Long id;
    private String name;
    private String filePath;
}
