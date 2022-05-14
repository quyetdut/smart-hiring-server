package com.example.smarthiring.service.impl;

import com.smartdev.iresource.authentication.converter.SignUpConverter;
import com.smartdev.iresource.authentication.dto.ChangePasswordDTO;
import com.smartdev.iresource.authentication.dto.SignUpDTO;
import com.smartdev.iresource.authentication.entity.Role;
import com.smartdev.iresource.authentication.entity.User;
import com.smartdev.iresource.authentication.enums.ConfirmationType;
import com.smartdev.iresource.authentication.enums.ERole;
import com.smartdev.iresource.authentication.exception.LoginException;
import com.smartdev.iresource.authentication.exception.NotFoundException;
import com.smartdev.iresource.authentication.exception.RegistrationFailedException;
import com.smartdev.iresource.authentication.repository.RoleRepository;
import com.smartdev.iresource.authentication.repository.UserRepository;
import com.smartdev.iresource.authentication.service.ConfirmationTokenService;
import com.smartdev.iresource.authentication.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Override
    public Boolean signupUser(SignUpDTO model) {
        if (checkUserExists(model.getEmail())) {
            throw new RegistrationFailedException("email " + model.getEmail() + " already taken", 1008);
        }

        User user = new User();

        Role role = roleRepository.findByName(ERole.valueOf(model.getRoleName()))
                .orElseThrow(() -> new RegistrationFailedException("Role input invalid", 1009));
        Set<Role> roles = new HashSet<Role>();

        roles.add(role);
        LocalDateTime createAt = LocalDateTime.now();

        SignUpConverter.convertModelSignupToUser(model, user);

        user.setCreateAt(createAt);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setUid(model.getUid());

        try {
            userRepository.save(user);
            confirmationTokenService.sendToken(user, ConfirmationType.SIGN_UP);
            return true;
        } catch (MessagingException e) {
            log.error(e.getMessage());
            throw new RegistrationFailedException(e.getMessage(), 1010);
        } catch (Exception e) {
            throw new RegistrationFailedException("registration failed", 1011);
        }
    }

    private boolean checkUserExists(String email) {
        if (userRepository.existsByEmail(email)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.getByEmail(email);
    }

    @Override
    public User getByEmail(String email) throws LoginException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new LoginException(
                        "User " + email + " doesn't exists.", 1201
                ));
    }

    @Override
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @Override
    public Map<String, String> getUid(Integer id) {

        Optional<User> user = userRepository.findById(id);
        Map<String, String> response = new HashMap<>();
        if(user.isEmpty()){
            throw new NotFoundException("id does not exist", 1250);
        }else {
            response.put("uid", user.get().getUid());
            return response;
        }
    }

    @Override
    public Integer getIdByUid(String Uid) {
        Optional<User> user = userRepository.getUserByUid(Uid);
        if (!user.isPresent()) return null;
        return user.get().getId();
    }

    @Override
    public Boolean resetPassword(ChangePasswordDTO changePasswordDTO) {
        User user = getByEmail(changePasswordDTO.getEmail());
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
        try {
            userRepository.save(user);
            return true;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return false;
        }
    }
}
