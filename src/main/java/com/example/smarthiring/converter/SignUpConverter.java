package com.example.smarthiring.converter;

import com.example.smarthiring.dto.SignUpDTO;
import com.example.smarthiring.entity.User;

public class SignUpConverter {
    public static User convertModelSignupToUser(SignUpDTO signUpDTO, User user) {
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());

        return user;
    }
}
