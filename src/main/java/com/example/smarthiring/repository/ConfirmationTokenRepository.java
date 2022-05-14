package com.smartdev.iresource.authentication.repository;

import com.smartdev.iresource.authentication.entity.ConfirmationToken;
import com.smartdev.iresource.authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    void removeConfirmationTokenByUser(User user);
}
