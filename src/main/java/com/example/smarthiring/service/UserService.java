package com.smartdev.iresource.authentication.service;

import com.smartdev.iresource.authentication.dto.ChangePasswordDTO;
import com.smartdev.iresource.authentication.dto.SignUpDTO;
import com.smartdev.iresource.authentication.entity.User;
import com.smartdev.iresource.authentication.exception.RegistrationFailedException;
import org.springframework.http.ResponseEntity;

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
