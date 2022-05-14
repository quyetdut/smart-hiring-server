package com.example.smarthiring.service;

import com.example.smarthiring.dto.ChangePasswordDTO;
import com.example.smarthiring.dto.SignUpDTO;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.exception.RegistrationFailedException;

import java.util.List;
import java.util.Map;

public interface UserService {
    Boolean signupUser(SignUpDTO model) throws RegistrationFailedException;

    User getByEmail(String email);

    List<User> getUser();

    Map<String, String> getUid(Integer id);

    Integer getIdByUid(String uid);

    Boolean resetPassword(ChangePasswordDTO changePasswordDTO);
}
