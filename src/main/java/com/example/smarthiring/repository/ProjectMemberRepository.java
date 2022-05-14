package com.smartdev.iresource.project.repository;

import com.smartdev.iresource.project.entity.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Integer> {
    Set<ProjectMember> findAllByProjectId(Integer projectId);

//    Set<ProjectMember> findAllByProjectIdAndStatus(Integer projectId, CollaborateStatus status);

    Optional<ProjectMember> findByProjectIdAndUserId(Integer projectId, Integer userId);

//    Page<ProjectMember> findDistinctByUserIdAndStatus(Integer userId, CollaborateStatus status, Pageable pageable);
//    List<ProjectMember> findDistinctByUserIdAndStatus(Integer userId, CollaborateStatus status);

    void deleteAllByProjectId(Integer id);

    void deleteByProjectIdAndUserId(Integer projectId, Integer userId);
}
