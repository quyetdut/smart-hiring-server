package com.smartdev.iresource.personal.repository;

import com.smartdev.iresource.personal.entity.PositionCapabilities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface PositionCapabilitiesRepository extends JpaRepository<PositionCapabilities, Integer> {
    Optional<PositionCapabilities> findByPositionIdAndCapabilitiesId(Integer positionId, Integer capabilitiesId);

    List<PositionCapabilities> findByPositionId(Integer positionId);
}
