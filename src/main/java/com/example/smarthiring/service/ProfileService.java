package com.example.smarthiring.services;

import com.smartdev.iresource.personal.common.enums.InterestingStatus;
import com.smartdev.iresource.personal.dto.*;

import java.util.List;
import java.util.Optional;

public interface ProfileService {

    Boolean createProfile(ProfileDto profile);

    Boolean isExistProfile(Integer userId);

    ProfileResponseDto getProfileByUserId(Integer userId);

    Boolean createProfilePO(ProfilePORequestDto profilePORequestDto);

    ProfilePOResponseDto getProfilePO(Integer userId);

    String getUsernameByUserId(Integer userId);

    List<ProjectCollaboratorDto> getProjectCollaborator(Integer projectId);

    List<PositionUserDetailDto> getPositionUsersDetail(Integer projectId, Integer positionId, InterestingStatus status);

    Optional<List<ProfileResponseDto>> getAll();
}
