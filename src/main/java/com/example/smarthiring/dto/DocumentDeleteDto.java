package com.smartdev.iresource.project.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DocumentDeleteDto {
    private Integer projectId;
    private Integer documentId;
    private String fileName;
}
