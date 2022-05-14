package com.example.smarthiring.common.vo.radarchart.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonaMatching {
    Integer positionId;
    List<MatchingSkill> matchingSkillList;
    List<Integer> userInterestedList;
    List<Integer> userNotInterestedList;
}
