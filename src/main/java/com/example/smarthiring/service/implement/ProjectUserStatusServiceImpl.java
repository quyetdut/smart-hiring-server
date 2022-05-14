package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.dto.UserPositionInteractReportDto;
import com.example.smarthiring.entity.ProjectMember;
import com.example.smarthiring.entity.ProjectUserStatus;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.exception.SomethingWrongException;
import com.example.smarthiring.repository.ProjectMemberRepository;
import com.example.smarthiring.repository.ProjectRepository;
import com.example.smarthiring.repository.ProjectUserStatusRepository;
import com.example.smarthiring.repository.UserRepository;
import com.example.smarthiring.service.ProjectMatchingService;
import com.example.smarthiring.service.ProjectMemberService;
import com.example.smarthiring.service.ProjectUserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
;import static com.example.smarthiring.common.ResponseMessage.INTERNAL_SERVER_ERROR;
import static com.example.smarthiring.common.ResponseMessage.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class ProjectUserStatusServiceImpl implements ProjectUserStatusService {

    private final ProjectUserStatusRepository projectUserStatusRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectMemberService projectMemberService;

    private final UserRepository userRepository;

    private final ProjectMatchingService projectMatchingService;

    @Override
    @Transactional
    public Boolean updateProjectUserStatus(ProjectUserStatusDTO projectUserStatusDTO) {
        try {
            if (
                    !userRepository.existsById(projectUserStatusDTO.getUserId())
                            || !projectRepository.existsById(projectUserStatusDTO.getProjectId())
            ) {
                throw new NotFoundException("projectId or userId is not exist");
            }

            ProjectUserStatus projectUserStatus;

            Optional<ProjectUserStatus> projectUserStatusOptional =
                    projectUserStatusRepository.findByProjectIdAndUserId(
                            projectUserStatusDTO.getProjectId(),
                            projectUserStatusDTO.getUserId()
                    );

            if (projectUserStatusOptional.isPresent()) {
                projectUserStatus = projectUserStatusOptional.get();

                if (projectUserStatusDTO.getCollaborateStatus() != null) {
                    projectUserStatus.setCollaborateStatus(projectUserStatusDTO.getCollaborateStatus());
                }
                if (projectUserStatusDTO.getInterestingStatus() != null) {
                    projectUserStatus.setInterestingStatus(projectUserStatusDTO.getInterestingStatus());
                }
                projectUserStatus.setUpdatedAt(LocalDateTime.now());
            } else {
                projectUserStatus = new ProjectUserStatus();
                projectUserStatus.setProjectId(projectUserStatusDTO.getProjectId());
                projectUserStatus.setUserId(projectUserStatusDTO.getUserId());
                projectUserStatus.setCollaborateStatus(projectUserStatusDTO.getCollaborateStatus());
                projectUserStatus.setInterestingStatus(projectUserStatusDTO.getInterestingStatus());
                projectUserStatus.setUpdatedAt(LocalDateTime.now());
            }

            if (projectUserStatusDTO.getPositionId() != null) {
                projectUserStatus.setPositionId(projectUserStatusDTO.getPositionId());
            }

            // add project member in case of PO send collaborate request or PO accept collaborate
            if (projectUserStatusDTO.getCollaborateStatus() == CollaborateStatus.COLLABORATING) {
                Optional<ProjectMember> projectMemberOptional = projectMemberRepository.findByProjectIdAndUserId(projectUserStatusDTO.getProjectId(), projectUserStatusDTO.getUserId());
                ProjectMember projectMember;
                if (projectMemberOptional.isPresent()) {
                    projectMember = projectMemberOptional.get();
                } else {
                    projectMember = new ProjectMember();
                    projectMember.setProjectId(projectUserStatusDTO.getProjectId());
                    projectMember.setUserId(projectUserStatusDTO.getUserId());
                }
                projectMember.setPositionId(projectUserStatus.getPositionId());
                projectMember.setUpdatedAt(LocalDateTime.now());
                projectMemberService.addProjectMember(projectMember);
            }

            // in case user reject collaborate remove user in project member
            if (projectUserStatusDTO.getCollaborateStatus() == CollaborateStatus.USER_REJECT_COLLABORATE) {
                projectMemberService.deleteProjectMember(projectUserStatusDTO.getProjectId(), projectUserStatusDTO.getUserId());
            }

            projectUserStatusRepository.save(projectUserStatus);

            return true;
        } catch (NotFoundException e) {
            throw new NotFoundException(NOT_FOUND);
        } catch (Exception e) {
            throw new SomethingWrongException(INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProjectUserStatusDTO getCollaborateStatus(Integer projectId, Integer userId) {
        if (
                !userRepository.existsById(userId)
                        || !projectRepository.existsById(projectId)
        ) {
            throw new NotFoundException("projectId or userId is not exist");
        }
        ProjectUserStatusDTO projectUserStatusDTO = new ProjectUserStatusDTO();
        projectUserStatusDTO.setProjectId(projectId);
        projectUserStatusDTO.setUserId(userId);
        Optional<ProjectUserStatus> projectUserStatusOptional =
                projectUserStatusRepository.findByProjectIdAndUserId(
                        projectUserStatusDTO.getProjectId(),
                        projectUserStatusDTO.getUserId()
                );
        if (projectUserStatusOptional.isPresent() && projectUserStatusOptional.get().getCollaborateStatus() != null) {
            projectUserStatusDTO.setCollaborateStatus(projectUserStatusOptional.get().getCollaborateStatus());
        } else {
            projectUserStatusDTO.setCollaborateStatus(CollaborateStatus.NOT_COLLABORATE);
        }

        return projectUserStatusDTO;
    }

    @Override
    public ProjectUserStatusDTO getInterestingStatus(Integer projectId, Integer userId) {
        if (!userRepository.existsById(userId) || !projectRepository.existsById(projectId)) {
            throw new NotFoundException("projectId or userId is not exist");
        }
        ProjectUserStatusDTO projectUserStatusDTO = new ProjectUserStatusDTO();
        projectUserStatusDTO.setProjectId(projectId);
        projectUserStatusDTO.setUserId(userId);
        Optional<ProjectUserStatus> projectUserStatusOptional =
                projectUserStatusRepository.findByProjectIdAndUserId(
                        projectUserStatusDTO.getProjectId(),
                        projectUserStatusDTO.getUserId()
                );
        if (projectUserStatusOptional.isPresent() && projectUserStatusOptional.get().getInterestingStatus() != null) {
            projectUserStatusDTO.setInterestingStatus(projectUserStatusOptional.get().getInterestingStatus());
        }

        return projectUserStatusDTO;
    }

    @Override
    public Boolean isCollaborating(Integer projectId, Integer userId) {
        Optional<ProjectUserStatus> projectUserStatusOptional = projectUserStatusRepository.findByProjectIdAndUserId(projectId, userId);

        if (projectUserStatusOptional.isPresent()) {
            if (projectUserStatusOptional.get().getCollaborateStatus() == CollaborateStatus.COLLABORATING)
                return true;
        }
        return false;
    }

    @Override
    public UserPositionInteractReportDto getUserPositionInteractReport(Integer projectId, Integer positionId) {
        Integer matchingCount = projectMatchingService.getCountMatching(projectId, positionId);
        List<ProjectUserStatus> projectUserStatuses = projectUserStatusRepository.findAllByProjectIdAndPositionId(projectId, positionId);
        UserPositionInteractReportDto userPositionInteractReportDto = new UserPositionInteractReportDto();
        userPositionInteractReportDto.setMatching(matchingCount);
        userPositionInteractReportDto.setInterested((int)projectUserStatuses.stream().filter(pus -> pus.getInterestingStatus() == InterestingStatus.INTERESTED).count());
        userPositionInteractReportDto.setRefused((int)projectUserStatuses.stream().filter(pus -> pus.getInterestingStatus() == InterestingStatus.NOT_INTERESTED).count());

        return userPositionInteractReportDto;
    }

    @Override
    public List<Integer> getUsersByInterestStatus(Integer projectId, InterestingStatus status) {
        List<ProjectUserStatus> projectUserStatus = projectUserStatusRepository.findAllByProjectIdAndInterestingStatus(projectId, status);
        if (CollectionUtils.isEmpty(projectUserStatus)) {
            return new ArrayList<>();
        } else {
            return projectUserStatus.stream().map(pus -> pus.getUserId()).collect(Collectors.toList());
        }
    }

}
