package com.example.smarthiring.repository;

import com.example.smarthiring.entity.Profiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProfileRepository extends JpaRepository<Profiles, Integer> {

    Optional<Profiles> findByUserId (Integer userId);

    List<Profiles> findAll();

    List<Profiles> findAllByUserIdIn(List<Integer> userId);

    Boolean existsByUserId(Integer userId);
}
