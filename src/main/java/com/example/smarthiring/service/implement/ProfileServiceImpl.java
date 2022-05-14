package com.example.smarthiring.services.implement;

import com.smartdev.iresource.personal.common.enums.ExceptionDefinition;
import com.smartdev.iresource.personal.common.enums.InterestingStatus;
import com.smartdev.iresource.personal.common.feignclient.AuthFeignClient;
import com.smartdev.iresource.personal.dto.*;
import com.smartdev.iresource.personal.dto.CapabilityLevelDto;
import com.smartdev.iresource.personal.entity.*;
import com.smartdev.iresource.personal.exceptions.FailException;
import com.smartdev.iresource.personal.exceptions.NotFoundException;
import com.smartdev.iresource.personal.mappers.ProfileMapper;
import com.smartdev.iresource.personal.repository.*;
import com.smartdev.iresource.personal.services.EnglishService;
import com.smartdev.iresource.personal.services.ProfileService;
import com.smartdev.iresource.personal.services.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCapabilitiesRepository profileCapabilitiesRepository;
    @Autowired
    private ProfileAwardRepository profileAwardRepository;
    @Autowired
    private CapabilitiesRepository capabilitiesRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private AwardRepository awardRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private WorkExperienceService workExperienceService;
    @Autowired
    private ProfilePositionRepository profilePositionRepository;
    @Autowired
    private CertificationRepository certificationRepository;
    @Autowired
    private WorkExperienceRepository workExperienceRepository;
    @Autowired
    private DivisionRepository divisionRepository;
    @Autowired
    private ProjectFeignClientServiceImpl projectFeignClientServiceImpl;
    @Autowired
    private ProjectMatchingRepository projectMatchingRepository;

    @Autowired
    private EnglishService englishService;

    @Autowired
    private AuthFeignClient authFeignClient;

    @Override
    public Boolean createProfile(ProfileDto profileDto) {
        Optional<Profiles> profileOptional = profileRepository.findByUserId(profileDto.getUserId());
        Profiles profile;
        if (profileOptional.isPresent()) {
            profile = profileOptional.get();
        } else {
            profile = new Profiles();
        }
        profile.setUserId(profileDto.getUserId());
        profile.setFirstName(profileDto.getFirstName());
        profile.setLastName(profileDto.getLastName());
        profile.setContractualTerm(profileDto.getContractualTerm());
        profile.setPosition(profileDto.getPosition());
        profile.setUpdatedAt(LocalDateTime.now());
        profile.setDivisionId(profileDto.getDivisionId());
        profile.setLocationId(profileDto.getLocationId());

        try {
            profileRepository.save(profile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FailException(ExceptionDefinition.CREATE_PROFILE_FAIL.getMessage(), ExceptionDefinition.CREATE_PROFILE_FAIL.getErrorCode());
        }

        return true;
    }

    @Override
    public Boolean isExistProfile(Integer userId) {
        return profileRepository.existsByUserId(userId);
    }


    private List<CapabilityLevelDto> getListCapabilities(Integer profileId) {
        List<CapabilityLevelDto> listCapabilities = new ArrayList<>();
        List<ProfileCapabilities> profileCapabilities = profileCapabilitiesRepository.findByProfileId(profileId);
        for (ProfileCapabilities profileCapabilities1 : profileCapabilities){
            Optional<Capabilities> capabilities = capabilitiesRepository.findById(profileCapabilities1.getCapabilitiesId());
            if (capabilities.isPresent()){
                CapabilityLevelDto capabilityLevelDto = new CapabilityLevelDto();
                capabilityLevelDto.setCapabilityId(capabilities.get().getId());
                capabilityLevelDto.setName(capabilities.get().getName());
                capabilityLevelDto.setLevel(profileCapabilities1.getLevel());
                listCapabilities.add(capabilityLevelDto);
            }
        }
        return listCapabilities;
    }

    private Set<String> getPositions(Integer profileId) {
        Set<String> positions = new HashSet<>();

        List<ProfilePosition> profilePositions = profilePositionRepository.findByProfileId(profileId);
        for (ProfilePosition profilePosition : profilePositions){
            Optional<Position> position = positionRepository.findById(profilePosition.getPositionId());
            if (position.isPresent()) {
                positions.add(position.get().getName());
            }
        }

        return positions;
    }

    private List<CertificationDto> getCertificates(Integer profileId) {
        List<CertificationDto> certifications = new ArrayList<>();
        List<Certification> certificationsGet = certificationRepository.findByProfileId(profileId);
        for (Certification certification : certificationsGet){
            CertificationDto certificationDto = new CertificationDto();
            certificationDto.setId(certification.getId());
            certificationDto.setName(certification.getName());
            certifications.add(certificationDto);
        }

        return certifications;
    }

    @Override
    public  ProfileResponseDto getProfileByUserId(Integer userId){
        // get profile
        Profiles profiles = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new FailException(
                        ExceptionDefinition.USER_NOT_FOUND.getMessage(),
                        ExceptionDefinition.USER_NOT_FOUND.getErrorCode())
                );

        // get englishScore
        Integer englishScore;
        Optional<English> english = englishService.getEnglish(profiles.getId());
        if (english.isPresent()) {
            englishScore = english.get().getLevel();
        } else englishScore = null;

        log.warn("pass english check");
        // get locations and division
        Locations locations = locationRepository.findById(profiles.getLocationId())
                .orElseThrow(() -> new FailException(ExceptionDefinition.LOCATION_NOT_FOUND.getMessage(), ExceptionDefinition.LOCATION_NOT_FOUND.getErrorCode()));
        Division division = divisionRepository.findById(profiles.getDivisionId())
                .orElseThrow(() -> new FailException(ExceptionDefinition.DIVISION_NOT_FOUND.getMessage(), ExceptionDefinition.DIVISION_NOT_FOUND.getErrorCode()));

        // get positions
        Set<String> positions = getPositions(profiles.getId());

        // get ListCapabilities
        List<CapabilityLevelDto> listCapabilities = getListCapabilities(profiles.getId());

        // get WorkExperiences
        List<WorkExperiences> workExperiences = workExperienceRepository.findByProfileId(profiles.getId());

        // get certifications
        List<CertificationDto> certifications = getCertificates(profiles.getId());

        return ProfileMapper.toProfileDtoResponse(
                profiles,locations,listCapabilities,positions, englishScore,workExperiences,certifications,division
        );
    }

    @Override
    public Boolean createProfilePO(ProfilePORequestDto profilePORequestDto) {
        Optional<Profiles> profiles = profileRepository.findByUserId( profilePORequestDto.getUserId());
        Division division = divisionRepository.findByName(profilePORequestDto.getDivision())
                .orElseThrow(() -> new FailException(ExceptionDefinition.DIVISION_NOT_FOUND.getMessage(), ExceptionDefinition.DIVISION_NOT_FOUND.getErrorCode()));
        if (profiles.isEmpty()){
            Profiles newProfiles = new Profiles();
            newProfiles.setUserId(profilePORequestDto.getUserId());
            newProfiles.setFirstName(profilePORequestDto.getFirstName());
            newProfiles.setLastName(profilePORequestDto.getLastName());
            newProfiles.setContractualTerm(profilePORequestDto.getContractualTerm());
            newProfiles.setDivisionId(division.getId());
            newProfiles.setUpdatedAt(LocalDateTime.now());
            try {
                Profiles profileSave = profileRepository.save(newProfiles);
                for ( String positionName : profilePORequestDto.getPositions()){
                    Position position = positionRepository.findByName(positionName)
                            .orElseThrow(() -> new NotFoundException(ExceptionDefinition.POSITION_NOT_FOUND.getMessage(), ExceptionDefinition.POSITION_NOT_FOUND.getErrorCode()));
                    ProfilePosition profilePosition = new ProfilePosition();
                    profilePosition.setProfileId(profileSave.getId());
                    profilePosition.setPositionId(position.getId());
                    profilePositionRepository.save(profilePosition);
                }
                return true;
            } catch (Exception e){
                throw new FailException(ExceptionDefinition.CREATE_PROFILE_PRODUCT_OWNER_FAIL.getMessage(), ExceptionDefinition.CREATE_PROFILE_PRODUCT_OWNER_FAIL.getErrorCode());
            }
        } else {
            throw new FailException(ExceptionDefinition.PROFILE_ALREADY_EXIST.getMessage(), ExceptionDefinition.PROFILE_ALREADY_EXIST.getErrorCode());
        }
    }

    @Override
    public ProfilePOResponseDto getProfilePO(Integer userId) {
        Profiles profiles = profileRepository.findByUserId(userId).
                orElseThrow(() -> new FailException(ExceptionDefinition.USER_NOT_FOUND.getMessage(), ExceptionDefinition.USER_NOT_FOUND.getErrorCode()));
        Division division = divisionRepository.findById(profiles.getDivisionId())
                .orElseThrow(() -> new FailException(ExceptionDefinition.DIVISION_NOT_FOUND.getMessage(), ExceptionDefinition.DIVISION_NOT_FOUND.getErrorCode()));
        List<Position> profilePositions = positionRepository.findAllById(profilePositionRepository.findByProfileId(profiles.getId()).stream().map(pp -> pp.getPositionId()).collect(Collectors.toList()));
        ProfilePOResponseDto profilePOResponseDto = new ProfilePOResponseDto(
                profiles.getUserId(),
                profiles.getFirstName(),
                profiles.getLastName(),
                division.getName(),
                profiles.getContractualTerm(),
                profilePositions
        );
        return profilePOResponseDto;
    }

    @Override
    public String getUsernameByUserId(Integer userId) {
        Optional<Profiles> profiles = profileRepository.findByUserId(userId);
        if (profiles.isPresent()) {
            return profiles.get().getFirstName() + " " + profiles.get().getLastName();
        }
        return "noname";
    }

    @Override
    public List<ProjectCollaboratorDto> getProjectCollaborator(Integer projectId) {
        List<ProjectCollaboratorDto> result = new ArrayList<>();
        List<ProjectUserStatusDto> psuList = projectFeignClientServiceImpl.getProjectMembers(projectId);
        psuList.forEach(psu -> {
            ProjectCollaboratorDto pc = new ProjectCollaboratorDto();
            pc.setUserId(psu.getUserId());

            if (!(psu.getPositionId() == null)) {
                Optional<Position> position = positionRepository.findById(psu.getPositionId());
                if (position.isPresent()) {
                    pc.setPositionName(position.get().getName());
                }
            } else {
                pc.setPositionName("No verification");
            }

            Optional<Profiles> profile = profileRepository.findByUserId(psu.getUserId());
            if (profile.isPresent()) {
                pc.setUserName(profile.get().getFirstName().concat(" ").concat(profile.get().getLastName()));
            } else {
                pc.setUserName("Noname");
            }
            result.add(pc);
        });

        return result;
    }

    @Override
    public List<PositionUserDetailDto> getPositionUsersDetail(Integer projectId, Integer positionId, InterestingStatus status) {
        List<PositionUserDetailDto> positionUserDetailDtoLst = new ArrayList<>();
        List<Integer> userIds = null;
        List<Integer> personaCapabilitiesId = projectFeignClientServiceImpl.getProjectPersonaCapabilities(projectId, positionId);
        Map<Integer, Capabilities> mapCapabilities = capabilitiesRepository.findAllById(personaCapabilitiesId).stream().collect(Collectors.toMap(Capabilities::getId, Function.identity()));
        if (status != null) {
            userIds = projectFeignClientServiceImpl.getUsersInterestStatus(projectId, status);
        }
        List<ProjectMatching> projectMatchings = projectMatchingRepository.findAllByProjectIdAndPositionId(projectId, positionId);
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<Integer> profileIds = profileRepository.findAllByUserIdIn(userIds).stream().map(p -> p.getId()).collect(Collectors.toList());
            projectMatchings = projectMatchings.stream().filter(pm -> profileIds.contains(pm.getProfileId())).collect(Collectors.toList());
        }

        if (status != null && CollectionUtils.isEmpty(userIds)) {
            return positionUserDetailDtoLst;
        }

        if (CollectionUtils.isNotEmpty(projectMatchings)) {
            Map<Integer, Profiles> mapProfiles = profileRepository.findAllById(projectMatchings.stream().map(p -> p.getProfileId())
                    .collect(Collectors.toList())).stream().collect(Collectors.toMap(Profiles::getId, Function.identity()));

            projectMatchings.forEach(pm -> {
                Profiles profile = mapProfiles.get(pm.getProfileId());
                Optional<Position> optionalPosition = positionRepository.findByName(profile.getPosition());
                Position activePosition = null;
                if (optionalPosition.isPresent()) {
                    activePosition = optionalPosition.get();
                }

                PositionUserDetailDto positionUserDetailDto = new PositionUserDetailDto();
                positionUserDetailDto.setMatching(pm.getMatchingScore());
                positionUserDetailDto.setName(profile.getFirstName().concat(" ").concat(profile.getLastName()));
                positionUserDetailDto.setUserId(profile.getUserId());
                positionUserDetailDto.setPositionName(profile.getPosition());
                positionUserDetailDto.setIcon(activePosition != null ? activePosition.getIcon() : "");
                positionUserDetailDto.setCapabilityLevelDto(getProfileCapability(personaCapabilitiesId, profile.getId(), mapCapabilities));
                positionUserDetailDtoLst.add(positionUserDetailDto);
            });
        }

        return positionUserDetailDtoLst;
    }

    private List<CapabilityLevelDto> getProfileCapability(List<Integer> personaCapabilitiesId, Integer profileId, Map<Integer, Capabilities> mapCapabilities) {
        List<CapabilityLevelDto> capabilityLevelDtoList = new ArrayList<>();
        List<ProfileCapabilities> profileCapabilities = profileCapabilitiesRepository.findByProfileId(profileId);
        profileCapabilities = profileCapabilities.stream().filter(pc -> personaCapabilitiesId.contains(pc.getCapabilitiesId())).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(profileCapabilities)) {
            profileCapabilities.forEach(pc -> {
                CapabilityLevelDto capabilityLevelDto = new CapabilityLevelDto();
                capabilityLevelDto.setCapabilityId(pc.getCapabilitiesId());
                capabilityLevelDto.setLevel(pc.getLevel());
                capabilityLevelDto.setName(mapCapabilities.get(pc.getCapabilitiesId()).getName());

                capabilityLevelDtoList.add(capabilityLevelDto);
            });
        }
        return capabilityLevelDtoList;
    }

    @Override
    public Optional<List<ProfileResponseDto>> getAll() {
        try {
            List<Profiles> all = profileRepository.findAll();

            List<ProfileResponseDto> profileDTOs = new ArrayList<>();
            all.forEach(profile -> {
                try {
                    authFeignClient.isExitPOId(profile.getUserId());
                } catch (Exception e) {
                    ProfileResponseDto profileElement = getProfileByUserId(profile.getUserId());
                    profileDTOs.add(profileElement);
                }
            });

            return Optional.of(profileDTOs);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("while getAllProfiles");

            return Optional.empty();
        }
    }
}
