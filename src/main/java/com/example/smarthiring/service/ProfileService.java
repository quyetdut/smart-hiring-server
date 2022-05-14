package com.example.smarthiring.service;

import com.example.smarthiring.dto.*;
import com.example.smarthiring.enums.InterestingStatus;

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

}
