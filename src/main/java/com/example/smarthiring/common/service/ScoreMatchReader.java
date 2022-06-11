package com.example.smarthiring.common.service;

import com.example.smarthiring.common.vo.UserSkills;
import com.example.smarthiring.dto.CapabilityLevelDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ScoreMatchReader  extends JdbcCursorItemReader<UserSkills> implements ItemReader<UserSkills> {

    public ScoreMatchReader(DataSource primaryDataSource) {
        setDataSource(primaryDataSource);
        setSql("SELECT p.id as id, GROUP_CONCAT(CONCAT(pc.capabilities_id, ' ', (pc.level)) SEPARATOR ',') as skill_data FROM profiles p JOIN profile_capabilities pc\n" +
                "                ON p.id = pc.profile_id GROUP BY p.id ORDER BY p.updated_at DESC;");
        setRowMapper(new ProfileIdRowMapper());
    }

    public class ProfileIdRowMapper implements RowMapper<UserSkills> {
        @Override
        public UserSkills mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserSkills userSkills = new UserSkills();
            userSkills.setProfileId(rs.getInt("id"));
            List<CapabilityLevelDto> capabilityLevelDtos = new ArrayList<>();
            String[] arrSkillLevel = rs.getString("skill_data").split(",");

            for (int i = 0; i < arrSkillLevel.length; i++) {
                CapabilityLevelDto capabilityLevelDto = new CapabilityLevelDto();
                String[] skillLevelArr = arrSkillLevel[i].split("\\s+");
                capabilityLevelDto.setCapabilityId(Integer.valueOf(skillLevelArr[0]));
                capabilityLevelDto.setLevel(Integer.valueOf(skillLevelArr[1]));
                capabilityLevelDtos.add(capabilityLevelDto);
            }

            userSkills.setCapabilityLevelDtos(capabilityLevelDtos);
            return userSkills;
        }
    }
}
