package com.smartdev.iresource.authentication.config;

import com.smartdev.iresource.authentication.common.feignclient.PersonalFeignClient;
import com.smartdev.iresource.authentication.exception.AuthEntryPointJwtException;
import com.smartdev.iresource.authentication.security.AuthEmailAndPasswordFilter;
import com.smartdev.iresource.authentication.security.AuthJwtTokenFilter;
import com.smartdev.iresource.authentication.service.impl.PersonaFeignClientService;
import com.smartdev.iresource.authentication.service.impl.UserServiceImpl;
import com.smartdev.iresource.authentication.utility.JwtUtils;
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
    private final PersonalFeignClient personalFeignClient;
    private final PersonaFeignClientService personaFeignClientService;

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
                personalFeignClient,
                personaFeignClientService

        );
        authEmailAndPasswordFilter.setFilterProcessesUrl("/sign-in");

        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/**", "/feign-client/**").permitAll()
                .anyRequest().authenticated();
        http
                .addFilter(authEmailAndPasswordFilter)
                .addFilterBefore(authJwtTokenFilter(), AuthEmailAndPasswordFilter.class);
    }
}