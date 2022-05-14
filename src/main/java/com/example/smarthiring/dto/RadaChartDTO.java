package com.smartdev.iresource.personal.dto;

import com.smartdev.iresource.personal.common.vo.ChartSkill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RadaChartDTO {
    Integer userId;
    String name;
    String icon;
    String positionName;
    Integer match;
    List<ChartSkill> capabilities;
}
