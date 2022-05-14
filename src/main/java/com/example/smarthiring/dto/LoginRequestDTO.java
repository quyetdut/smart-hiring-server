package com.example.smarthiring.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class LoginRequestDTO {
    @Email(message = "email is not valid")
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
