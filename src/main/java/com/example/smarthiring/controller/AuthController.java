package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.ChangePasswordDTO;
import com.example.smarthiring.dto.SignUpDTO;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.enums.ConfirmToken;
import com.example.smarthiring.enums.ConfirmationType;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.exception.ResponseBodyException;
import com.example.smarthiring.service.ConfirmationTokenService;
import com.example.smarthiring.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@AllArgsConstructor
@Slf4j
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/sign-in")
    public void signIn() {
    }

    @PostMapping("/sign-up")
    @ResponseBody
    public ResponseEntity<Object> signupUser(@Valid @RequestBody SignUpDTO model) {
        if (userService.signupUser(model)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/sign-up/confirm")
    public ResponseEntity<?> confirmUser(@Valid @RequestParam("token") String token) {
        ConfirmToken confirmToken = confirmationTokenService.confirmToken(token);
        if (confirmToken == ConfirmToken.CONFIRMED) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, confirmToken));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseBodyException(confirmToken.getResult(), HttpStatus.BAD_REQUEST, confirmToken.getCode(), confirmToken.getMessage()));
        }
    }

    @GetMapping("/sign-up/resend-token")
    public ResponseEntity<?> resendToken(@Valid @RequestParam("email") String email) {
        if (confirmationTokenService.resendToken(email, ConfirmationType.SIGN_UP)) {
            ResponseResult resultEnum = ResponseResult.SUCCESS;
            resultEnum.customMessage("Please check your email!");
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, resultEnum));
        } else {
            return ResponseEntity.ok().body(new ResponseBodyException(
                    false, HttpStatus.BAD_REQUEST, 1007, "Can't resend token."
            ));
        }
    }
    @GetMapping("/get-uid/{userId}")
    @PreAuthorize("hasRole('ROLE_DEV') or hasRole('ROLE_PO') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Object> getUid( @PathVariable Integer userId){
        Map<String, String> response = userService.getUid(userId);
        return ResponseEntity.ok(ResponseHandler.getInstance().response(response, ResponseResult.SUCCESS));
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestParam("email") String email) {
        User user = userService.getByEmail(email);
        try {
            confirmationTokenService.sendToken(user, ConfirmationType.FORGOT_PASSWORD);
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/forgot-password/resend-token")
    public ResponseEntity<?> resendForgotPassword(@Valid @RequestParam("email") String email) {
        if (confirmationTokenService.resendToken(email, ConfirmationType.FORGOT_PASSWORD)) {
            ResponseResult resultEnum = ResponseResult.SUCCESS;
            resultEnum.customMessage("Please check your email!");
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, resultEnum));
        } else {
            return ResponseEntity.ok().body(new ResponseBodyException(
                    false, HttpStatus.BAD_REQUEST, 1007, "Can't resend token."
            ));
        }
    }

    @GetMapping("/forgot-password/confirm")
    public ResponseEntity<?> confirmForgotPassword(@Valid @RequestParam("token") String token) {
        ConfirmToken confirmToken = confirmationTokenService.confirmToken(token);
        if (confirmToken == ConfirmToken.CONFIRMED) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, confirmToken));
        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ResponseBodyException(confirmToken.getResult(), HttpStatus.BAD_REQUEST, confirmToken.getCode(), confirmToken.getMessage()));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> confirmForgotPassword(@Valid @RequestBody ChangePasswordDTO model) {
        if (userService.resetPassword(model)) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

}