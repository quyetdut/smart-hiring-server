package com.example.smarthiring.dto;

import com.example.smarthiring.entity.Awards;
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
