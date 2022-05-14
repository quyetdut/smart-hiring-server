package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMatchingScoreDTO {
    String ProjectName;
    String TitleNeed;
    String LevelNeed;
    String SkillNeed;
}
