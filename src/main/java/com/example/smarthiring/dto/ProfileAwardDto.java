package com.smartdev.iresource.personal.dto;

import com.smartdev.iresource.personal.entity.Awards;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileAwardDto {
    private Integer userId;
    private Set<Awards> awards;
}
