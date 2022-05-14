package com.example.smarthiring.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private Boolean isEnable;
    private Boolean isLooked;
    private String createAt;
    private String updateAt;
    private Set<String> roles;
    private Boolean isProfileCreated;
    private String uid;
}
