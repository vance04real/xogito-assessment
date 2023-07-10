package com.xogito.projectmanagement.service.api;


import com.xogito.projectmanagement.dtos.request.UpdateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UserProjectAssignmentRequest;
import com.xogito.projectmanagement.dtos.request.CreateProjectRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.ProjectResponse;
import com.xogito.projectmanagement.dtos.response.ProjectWithoutAssignedUsersResponse;
import org.springframework.data.domain.Pageable;

public interface ProjectService {

    ApiResponse createProject(CreateProjectRequest createProjectRequest);
    ProjectWithoutAssignedUsersResponse findProjectsWithoutAssignedUsers(Pageable pageable);
    ApiResponse  assignProjectToUser(UserProjectAssignmentRequest userProjectAssignmentRequest);
    ProjectResponse findProjectsWithUnAssignedUsers(Pageable pageable);
    ProjectResponse  searchProjects(String name, Pageable pageable);
    ApiResponse deleteProject(Long id);
    ApiResponse updateProject(Long id, UpdateProjectRequest updateProjectRequest);
}
