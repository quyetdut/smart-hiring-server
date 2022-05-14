package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProfilePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ProfilePositionRepository extends JpaRepository<ProfilePosition, Integer> {
    Optional<ProfilePosition> findByProfileIdAndPositionId( Integer profileId, Integer positionId);

    long deleteAllByProfileId(Integer profileId);

    List<ProfilePosition> findByProfileId(Integer profileId);

    List<ProfilePosition> findByPositionId(Integer positionId);

    Boolean existsByProfileIdAndPositionId(Integer profileId, Integer positionId);
}
