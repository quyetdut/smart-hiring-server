package com.smartdev.iresource.authentication.converter;

import com.smartdev.iresource.authentication.dto.SignUpDTO;
import com.smartdev.iresource.authentication.entity.User;

public class SignUpConverter {
    public static User convertModelSignupToUser(SignUpDTO signUpDTO, User user) {
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(signUpDTO.getPassword());

        return user;
    }
}
