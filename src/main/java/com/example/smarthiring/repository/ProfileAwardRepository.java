package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProfileAwards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(readOnly = true)
public interface ProfileAwardRepository extends JpaRepository<ProfileAwards, Integer > {
    Long deleteAllByProfileId(Integer profileId);
}
