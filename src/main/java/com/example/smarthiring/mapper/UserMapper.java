package com.example.smarthiring.mapper;

import com.example.smarthiring.dto.UserDTO;
import com.example.smarthiring.entity.Role;
import com.example.smarthiring.entity.User;

import java.util.HashSet;
import java.util.Set;

public class UserMapper {
    public static UserDTO userMapper(User user, boolean hasProfile) {
        UserDTO user1 = new UserDTO();
        Set<String> temp = new HashSet<>();

        user1.setId(user.getId());
        user1.setEmail(user.getEmail());
        user1.setIsLooked(user.getIsLooked());
        user1.setIsEnable(user.getIsEnabled());
        user1.setUid(user.getUid());
        user1.setCreateAt(user.getCreateAt().toString());
        if (user.getUpdateAt() != null){
            user1.setUpdateAt(user.getUpdateAt().toString());
        }
        for (Role role: user.getRoles()){
            temp.add(role.getName().toString());
        }
        user1.setRoles(temp);
        user1.setIsProfileCreated(hasProfile);

        return  user1;
    }
}
