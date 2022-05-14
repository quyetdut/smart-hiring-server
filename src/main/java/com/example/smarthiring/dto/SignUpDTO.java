package com.example.smarthiring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDTO {
    private String firstName;
    private String lastName;

    @Email(message = "email is not valid")
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String roleName;

    private String uid;
}
