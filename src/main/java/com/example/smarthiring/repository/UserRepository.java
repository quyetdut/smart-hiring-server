package com.example.smarthiring.repository;

import com.example.smarthiring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.isEnabled = true WHERE u.email = ?1")
    void enabledUser(String email);

    @Override
    Optional<User> findById(Integer integer);

    Optional<User> getUserByUid(String Uid);
}
