package com.smartdev.iresource.authentication.repository;

import com.smartdev.iresource.authentication.enums.ERole;
import com.smartdev.iresource.authentication.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole roleName);
}
