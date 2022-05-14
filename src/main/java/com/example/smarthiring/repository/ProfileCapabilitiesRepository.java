package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProfileCapabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProfileCapabilitiesRepository extends JpaRepository<ProfileCapabilities, Integer> {
    Long deleteAllByProfileId(Integer profileId);

    List<ProfileCapabilities> findByProfileId(Integer profileId);

    Optional<ProfileCapabilities> findByProfileIdAndCapabilitiesId(Integer profileId, Integer capabilityId);
}
