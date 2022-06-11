package com.example.smarthiring.config;

import com.example.smarthiring.exception.AuthEntryPointJwtException;
import com.example.smarthiring.security.AuthEmailAndPasswordFilter;
import com.example.smarthiring.security.AuthJwtTokenFilter;
import com.example.smarthiring.service.ProfileService;
import com.example.smarthiring.service.implement.UserServiceImpl;
import com.example.smarthiring.utility.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceImpl userService;
    private final AuthEntryPointJwtException unauthorizedHandler;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private final ProfileService profileService;

    @Bean
    public AuthJwtTokenFilter authJwtTokenFilter() {
        return new AuthJwtTokenFilter();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userService)
                .passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        AuthEmailAndPasswordFilter authEmailAndPasswordFilter = new AuthEmailAndPasswordFilter(
                userService,
                this.authenticationManager(),
                jwtUtils,
                profileService
        );
        authEmailAndPasswordFilter.setFilterProcessesUrl("/auth/sign-in");

        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/auth/**","/project/fileStore/**").permitAll()
                .anyRequest().authenticated();
        http
                .addFilter(authEmailAndPasswordFilter)
                .addFilterBefore(authJwtTokenFilter(), AuthEmailAndPasswordFilter.class);
    }
}