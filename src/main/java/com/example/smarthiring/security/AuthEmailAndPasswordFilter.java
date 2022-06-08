package com.example.smarthiring.security;

import com.example.smarthiring.dto.LoginRequestDTO;
import com.example.smarthiring.dto.UserDTO;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.exception.ResponseBodyException;
import com.example.smarthiring.mapper.UserMapper;
import com.example.smarthiring.service.ProfileService;
import com.example.smarthiring.service.UserService;
import com.example.smarthiring.utility.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class AuthEmailAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    private final ProfileService profileService;
    private Logger logger = LoggerFactory.getLogger(AuthEmailAndPasswordFilter.class);



    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        LoginRequestDTO authenticationRequest = new ObjectMapper()
                .readValue(request.getInputStream(), LoginRequestDTO.class);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );
            Authentication authenticate = authenticationManager.authenticate(authentication);
        return authenticate;
    }

    public UserDTO userMapper(String username) {
        User user = userService.getByEmail(username);
        return UserMapper.userMapper(user, isExistProfile(user.getId()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User userDetails = (User) authResult.getPrincipal();

        UserDTO user = userMapper(userDetails.getEmail());
        user.setIsProfileCreated(isExistProfile(user.getId()));
        user.setName(profileService.getUsernameByUserId(user.getId()));
        String jwt = jwtUtils.generateJwtToken(authResult);

        Map<String, Object> responseBody = new LinkedHashMap<>();

        Map<String, Object> responseData = new LinkedHashMap<>();

        responseData.put("user", user);
        responseData.put("access_token", jwt);

        responseBody.put("message", "Success");
        responseBody.put("data", responseData);

        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), responseBody);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        String message = "Email or password is invalid.";
        String confirmTokenMessage = "Please confirm token!";

        logger.warn("FailedAuthedException className: " + failed.getClass().getName());
        logger.warn("FailedAuthedException message: " + failed.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);

        ResponseBodyException responseBodyException;

        if (failed.getClass().getName().equals(DisabledException.class.getName())) {
            responseBodyException = new ResponseBodyException(
                    false, HttpStatus.BAD_REQUEST, 1013, confirmTokenMessage
            );
        } else {
            responseBodyException = new ResponseBodyException(
                    false, HttpStatus.BAD_REQUEST, 1002, message
            );
        }

        new ObjectMapper().writeValue(response.getOutputStream(), responseBodyException);
    }

    private Boolean isExistProfile(Integer id) {
        try {
            Boolean isCreated = profileService.isExistProfile(id);
            return isCreated;
        } catch (Exception e) {
            logger.warn(e.getMessage());
            return false;
        }
    }
}
