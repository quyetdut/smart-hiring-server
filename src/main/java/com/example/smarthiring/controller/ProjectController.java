package com.example.smarthiring.controller;

import com.example.smarthiring.common.response.ResponseHandler;
import com.example.smarthiring.dto.*;
import com.example.smarthiring.enums.CollaborateStatus;
import com.example.smarthiring.enums.InterestingStatus;
import com.example.smarthiring.enums.ResponseResult;
import com.example.smarthiring.exception.FileStorageException;
import com.example.smarthiring.exception.RequestFailedException;
import com.example.smarthiring.service.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.smarthiring.common.ErrorLog.UPDATE_PROJECT_FAILED;
import static com.example.smarthiring.common.ResponseMessage.NO_CONTENT;


/*@CrossOrigin(origins = "*", maxAge = 3600)*/
@RestController
@RequestMapping("/project/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get-all-projects-name")
    public ResponseEntity<Object> getAllProjectNames() {
        Optional<List<String>> allProjectNames = projectService.getAllProjectNames();

        if (allProjectNames.isPresent()) return ResponseEntity.ok(ResponseHandler.getInstance().response(allProjectNames.get(), ResponseResult.SUCCESS));
        return ResponseEntity.internalServerError().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createProject(
            @RequestParam(value = "poId") Integer poId,
            @RequestParam(value = "projectInfo") String projectInfo,
            @RequestParam(value = "image", required = false) MultipartFile projectImage
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            ProjectInfoDTO projectInfoDTO = objectMapper.readValue(
                    projectInfo, ProjectInfoDTO.class);

            return ResponseEntity.ok(ResponseHandler.getInstance().response(projectService.createProject(projectInfoDTO, poId, projectImage), ResponseResult.SUCCESS));
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("Create project failed: {} {}", ex.getClass(), ex.getMessage());
            throw new FileStorageException("error: " + ex.getClass());
        }
    }

    @GetMapping("/get-all")
    @ResponseBody
    public ResponseEntity<Object> getAllProject(
            @RequestParam(value = "poId", required = false) Integer poId,
            @RequestParam(value = "page", required = false) @Min(0) Integer page,
            @RequestParam(value = "projectTitle", required = false, defaultValue = "") String projectTitle
    ) {
        Map<String, Object> result = projectService.getAllProject(poId, page, projectTitle);
        if (result != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
        } else {
            ResponseResult responseResult = ResponseResult.SUCCESS;
            responseResult.customMessage(NO_CONTENT);
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, responseResult));
        }
    }

    @GetMapping("/{projectId}")
    @ResponseBody
    public ResponseEntity<Object> getProject(@PathVariable(value = "projectId") Integer projectId) {
        ProjectResponseDTO result = projectService.getProject(projectId);
        if (result != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
        } else {
            ResponseResult responseResult = ResponseResult.SUCCESS;
            responseResult.customMessage(NO_CONTENT);
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, responseResult));
        }
    }

    @GetMapping("/project-skills/{projectId}")
    public Object getProjectSkills(@PathVariable(value = "projectId") Integer projectId) {
        ProjectSkillsDTO result = projectService.getProjectSkills(projectId);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    @GetMapping("/get-interested-projects")
    public ResponseEntity<Object> getInterestedProjects(@RequestParam(value = "userId") Integer userId) {
        List<ProjectResponseDTO> result = projectService.getInterestedProjects(userId, InterestingStatus.INTERESTED);
        if (result != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
        } else {
            ResponseResult responseResult = ResponseResult.SUCCESS;
            responseResult.customMessage(NO_CONTENT);
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, responseResult));
        }
    }

    @GetMapping("/get-collaborate-projects")
    public ResponseEntity<Object> getCollaborateProjects(@RequestParam(value = "userId") Integer userId) {
        List<ProjectResponseDTO> result = projectService.getCollaborateProjects(userId, CollaborateStatus.COLLABORATING);
        if (result != null) {
            return ResponseEntity.ok(ResponseHandler.getInstance().response(result, ResponseResult.SUCCESS));
        } else {
            ResponseResult responseResult = ResponseResult.SUCCESS;
            responseResult.customMessage(NO_CONTENT);
            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, responseResult));
        }
    }

    @GetMapping("/get-projects-by-ids")
    @ResponseBody
    public Map<Integer, Object> getProjectsByIdList(@RequestParam(value = "ids") List<Integer> ids) {
        return projectService.getMapProjects(ids);
    }

    @PutMapping("/update/{poID}")
    public ResponseEntity<Object> updateProject(
            @RequestBody ProjectCreationDTO project,
            @PathVariable(value = "poID") Integer poID
    ) {
        throw new RequestFailedException(UPDATE_PROJECT_FAILED);
//        if (projectService.updateProject(project, poID)) {
//            return ResponseEntity.ok(ResponseHandler.getInstance().response(null, ResponseResult.SUCCESS));
//        } else {
//            log.error("Update Project Method in ProjectController: Update Failed!");
//            throw new RequestFailedException(UPDATE_PROJECT_FAILED);
//        }
    }
}
