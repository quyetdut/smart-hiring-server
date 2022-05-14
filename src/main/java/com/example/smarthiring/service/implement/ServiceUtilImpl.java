package com.example.smarthiring.service.implement;

import com.example.smarthiring.dto.ProjectSkillsDTO;
import com.example.smarthiring.dto.ProjectUserStatusDTO;
import com.example.smarthiring.entity.ProjectMember;
import com.example.smarthiring.entity.Role;
import com.example.smarthiring.entity.User;
import com.example.smarthiring.enums.ERole;
import com.example.smarthiring.enums.InterestingStatus;
import com.example.smarthiring.exception.NotFoundException;
import com.example.smarthiring.repository.ProjectMemberRepository;
import com.example.smarthiring.repository.UserRepository;
import com.example.smarthiring.security.JwtConfig;
import com.example.smarthiring.service.ProjectPersonasService;
import com.example.smarthiring.service.ProjectService;
import com.example.smarthiring.service.ProjectUserStatusService;
import com.example.smarthiring.service.ServiceUtil;
import com.example.smarthiring.utility.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceUtilImpl implements ServiceUtil {
    private final JwtUtils jwtUtils;
    private final JwtConfig jwtConfig;
    private final UserRepository userRepository;
    private ProjectPersonasService projectPersonasService;

    private ProjectMemberRepository projectMemberRepository;

    @Override
    public Boolean isTokenTruth(HttpServletRequest request) {
        String token = null;
        String headerAuth = request.getHeader(jwtConfig.getAuthorizationHeader());

        String tokenPrefix = jwtConfig.getTokenPrefix();
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(tokenPrefix)) {
            token = headerAuth.replace(tokenPrefix, "");
        }
        try{
            jwtUtils.validateJwtToken(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Boolean isExitPOId(Integer id) {
        User POUser = userRepository.findById(id).orElseThrow(
                () -> new NotFoundException("ko thay User by ID")
        );

        Role PORole = POUser.getRoles().stream().findFirst().get();
        if (PORole.getName().equals(ERole.ROLE_PO)) return true;
        throw new NotFoundException("ID is another Role, not PO-Role", 1101);
    }

    @Override
    public Boolean isExistUserById(Integer id) {
        Optional<User> byId = userRepository.findById(id);
        if (!byId.isPresent()) {
            return true;
        } else return false;
    }

    @Override
    public List<Integer> getProjectPersonaCapabilities( Integer projectId, Integer positionId) {
        return projectPersonasService.getCapabilitiesId(projectId, positionId);
    }

    @Override
    public List<ProjectUserStatusDTO> getProjectMembers(Integer projectId) {
        List<ProjectUserStatusDTO> result = new ArrayList<>();

        Set<ProjectMember> projectMembers = projectMemberRepository.findAllByProjectId(projectId);
        projectMembers.forEach(pm -> {
            ProjectUserStatusDTO pus = new ProjectUserStatusDTO();
            pus.setProjectId(projectId);
            pus.setUserId(pm.getUserId());
            pus.setPositionId(pm.getPositionId());
            result.add(pus);
        });

        return result;
    }
}
