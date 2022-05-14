package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Role;
import com.example.smarthiring.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole roleName);
}
