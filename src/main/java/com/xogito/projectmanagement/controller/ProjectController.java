package com.xogito.projectmanagement.controller;

import com.xogito.projectmanagement.dtos.request.CreateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UpdateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UserProjectAssignmentRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.ProjectResponse;
import com.xogito.projectmanagement.dtos.response.ProjectWithoutAssignedUsersResponse;
import com.xogito.projectmanagement.service.api.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;


    @PostMapping
    public ResponseEntity<ApiResponse> createProject(@RequestBody @Valid CreateProjectRequest createProjectRequest) {
        ApiResponse apiResponse = projectService.createProject(createProjectRequest);
        return ResponseEntity.ok(apiResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long id) {

        ApiResponse apiResponse = projectService.deleteProject(id);

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long id, @RequestBody UpdateProjectRequest updateProjectRequest) {
        ApiResponse apiResponse = projectService.updateProject(id, updateProjectRequest);

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<ProjectResponse> searchProjects(@RequestParam String name, @RequestParam(defaultValue = "0", required = false)
    Integer page,
                                                          @RequestParam(defaultValue = "5", required = false)
                                                          Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);

        ProjectResponse projectResponse = projectService.searchProjects(name, paging);

        return ResponseEntity.ok(projectResponse);

    }


    @GetMapping("/retrieveWithoutUnAssignedUsers")
    public ResponseEntity<ProjectResponse> findUnAssignedProjects(@RequestParam(defaultValue = "0", required = false)
                                                                  Integer page,
                                                                  @RequestParam(defaultValue = "5", required = false)
                                                                  Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);

        ProjectResponse projectsWithUnAssignedUsers = projectService.findProjectsWithUnAssignedUsers(paging);

        return ResponseEntity.ok(projectsWithUnAssignedUsers);

    }

    @GetMapping("/retrieveWithoutUsers")
    public ResponseEntity<ProjectWithoutAssignedUsersResponse> findProjectsWithoutAssignedusers(
            @RequestParam(defaultValue = "0", required = false)
            Integer page,
            @RequestParam(defaultValue = "5", required = false)
            Integer pageSize) {

        Pageable paging = PageRequest.of(page, pageSize);

        ProjectWithoutAssignedUsersResponse projectsWithoutAssignedUsers = projectService.findProjectsWithoutAssignedUsers(paging);

        return ResponseEntity.ok(projectsWithoutAssignedUsers);

    }

    @PostMapping("/assignUser")
    public ResponseEntity<ApiResponse> assignProject(@RequestBody UserProjectAssignmentRequest userProjectAssignmentRequest) {

        ApiResponse apiResponse = projectService.assignProjectToUser(userProjectAssignmentRequest);

        return ResponseEntity.ok(apiResponse);
    }


}
