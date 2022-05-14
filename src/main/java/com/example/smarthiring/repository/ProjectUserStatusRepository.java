package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.common.enums.CollaborateStatus;
import com.smartdev.iresource.project.common.enums.InterestingStatus;
import com.smartdev.iresource.project.entity.ProjectUserStatus;
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
