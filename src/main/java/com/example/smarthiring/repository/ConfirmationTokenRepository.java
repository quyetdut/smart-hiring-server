package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ConfirmationToken;
import com.example.smarthiring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    void removeConfirmationTokenByUser(User user);
}
