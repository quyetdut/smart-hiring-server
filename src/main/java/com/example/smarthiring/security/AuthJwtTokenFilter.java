package com.example.smarthiring.security;

import com.example.smarthiring.exception.ResponseBodyException;
import com.example.smarthiring.service.UserService;
import com.example.smarthiring.utility.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
public class AuthJwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = parseJwt(httpServletRequest);
//        String jwt = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJxdXlldHBtMUB5b3BtYWlsLmNvbSIsImlhdCI6MTY1NDkyMjAzOSwiZXhwIjoxNjU0OTY2ODAwfQ.xEcSJDbAp5unSiZI7qAD701wXTBiFY1EFaSDop7PD_u740W-_LNi6UY-bsybLvsGLYDtTE9ae1GkoseyqcZ2ng";
        try {
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String email = jwtUtils.getEmailFromJwtToken(jwt);

                // authentication token from database
                UserDetails userDetails = userService.getByEmail(email);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );

                // authentication token from request
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                // authentication step
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e) {
            ResponseBodyException responseBodyException
                    = new ResponseBodyException(false, HttpStatus.BAD_REQUEST, 1999, e.getMessage());
            new ObjectMapper().writeValue(httpServletResponse.getOutputStream(), responseBodyException);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(jwtConfig.getAuthorizationHeader());

        String tokenPrefix = jwtConfig.getTokenPrefix();
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(tokenPrefix)) {
            return headerAuth.replace(tokenPrefix, "");
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }
}