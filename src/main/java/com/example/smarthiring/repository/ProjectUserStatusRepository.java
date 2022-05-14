package com.example.smarthiring.repository;

import com.example.smarthiring.entity.ProjectUserStatus;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectUserStatusRepository extends JpaRepository<ProjectUserStatus, Integer> {
    Optional<ProjectUserStatus> findByProjectIdAndUserId(Integer projectId, Integer userId);

    List<ProjectUserStatus> findAllByProjectIdAndInterestingStatus(Integer projectId, InterestingStatus status);

    List<ProjectUserStatus> findAllByUserIdAndCollaborateStatus(Integer userId, CollaborateStatus collaborateStatus);

    List<ProjectUserStatus> findAllByUserIdAndInterestingStatus(Integer userId, InterestingStatus interestingStatus);

    List<ProjectUserStatus> findAllByProjectIdAndPositionId(Integer projectId, Integer positionId);
}
