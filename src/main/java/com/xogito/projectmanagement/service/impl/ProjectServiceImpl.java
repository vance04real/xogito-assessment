package com.xogito.projectmanagement.service.impl;

import com.xogito.projectmanagement.dtos.request.CreateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UpdateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UserProjectAssignmentRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.ProjectResponse;
import com.xogito.projectmanagement.dtos.response.ProjectWithoutAssignedUsersResponse;
import com.xogito.projectmanagement.dtos.response.UserResponse;
import com.xogito.projectmanagement.entity.Project;
import com.xogito.projectmanagement.exceptions.NotFoundException;
import com.xogito.projectmanagement.exceptions.XogitoUserException;
import com.xogito.projectmanagement.repository.ProjectRepository;
import com.xogito.projectmanagement.repository.UserRepository;
import com.xogito.projectmanagement.service.api.ProjectService;
import com.xogito.projectmanagement.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserRepository userRepository;
    @Override
    public ApiResponse createProject(CreateProjectRequest createProjectRequest) {
        log.info("User Request received {}", createProjectRequest);

       var optionalProject = projectRepository.findProjectByNameIgnoreCase(createProjectRequest.getName());

        if(optionalProject.isPresent()){
            throw new XogitoUserException(AppConstants.PROJECT_ALREADY_EXISTS);
        }
        projectRepository.save(Project.builder()
                .name(createProjectRequest.getName())
                .description(createProjectRequest.getDescription())
                .build());

        return ApiResponse.builder()
                .message(AppConstants.PROJECT_CREATED_SUCCESSFUL)
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public ProjectWithoutAssignedUsersResponse findProjectsWithoutAssignedUsers(Pageable pageable) {

        var retrievedProject = projectRepository.findBy(pageable);
        return  ProjectWithoutAssignedUsersResponse.builder()
                .message(AppConstants.PROJECT_RETRIEVED_SUCCESSFUL)
                .projectList(retrievedProject)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ApiResponse assignProjectToUser(UserProjectAssignmentRequest userProjectAssignmentRequest) {
        log.info("User Project Assignment Request received {}", userProjectAssignmentRequest);

        var retrievedUser = userRepository.findById(userProjectAssignmentRequest.getUserId()).orElseThrow(() -> new NotFoundException(AppConstants.USER_NOT_FOUND));
        var retrievedProject = projectRepository.findById(userProjectAssignmentRequest.getProjectId()).orElseThrow(() -> new NotFoundException(AppConstants.PROJECT_NOT_FOUND));

        retrievedProject.getAssignedUsers().add(retrievedUser);
        retrievedUser.getAssignedProjects().add(retrievedProject);

        projectRepository.save(retrievedProject);

        userRepository.save(retrievedUser);

        return  ApiResponse.builder()
                .message(AppConstants.PROJECT_ASSIGNED_TO_USER)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ProjectResponse findProjectsWithNoUsersAssignedToThem(Pageable pageable) {
        //TODO fix this method
        var retrievedProject = projectRepository.findProjectsWithNoUsersAssignedToThem(pageable);
        return  ProjectResponse.builder()
                .message(AppConstants.PROJECT_RETRIEVED_SUCCESSFUL)
                .projectList(retrievedProject)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ProjectResponse searchProjects(String name, Pageable pageable) {
        log.info("Name parameter received  {}", name);
        var retrievedProjects = projectRepository.findProjectByNameIgnoreCase(name, pageable);
        if(retrievedProjects.getTotalElements() == 0){
            log.info("No results found");
            return ProjectResponse.builder()
                    .message(AppConstants.NO_RESULTS_FOUND)
                    .code(HttpStatus.NO_CONTENT.value())
                    .build();
        }
        return ProjectResponse.builder()
                .message(AppConstants.PROJECT_RETRIEVED_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .projectList(retrievedProjects)
                .build();
    }

    @Override
    public ApiResponse deleteProject(Long id) {
        try {
            projectRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("Error occurred on deletion {}", ex.getMessage());
            throw new XogitoUserException(AppConstants.PROJECT_TO_BE_DELETED_NOT_FOUND);
        }

        return ApiResponse.builder()
                .message(AppConstants.PROJECT_DELETE_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ApiResponse updateProject(Long id, UpdateProjectRequest updateProjectRequest) {

        var project = projectRepository.findById(id).orElseThrow(()-> new NotFoundException(AppConstants.PROJECT_NOT_FOUND));

        if (Objects.nonNull(updateProjectRequest.getName()) && !"".equalsIgnoreCase(updateProjectRequest.getName())) {

            project.setName(updateProjectRequest.getName());
        }

        if (Objects.nonNull(updateProjectRequest.getDescription()) && !"".equalsIgnoreCase(updateProjectRequest.getDescription())) {

            project.setDescription(updateProjectRequest.getDescription());
        }

        projectRepository.save(project);

        return ApiResponse.builder()
                .message(AppConstants.PROJECT_UPDATE_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ProjectResponse findAllProjects(Pageable pageable) {

       var projects = projectRepository.findAll(pageable);

        if(projects.getTotalElements() == 0){
            return ProjectResponse.builder()
                    .message(AppConstants.NO_RESULTS_FOUND)
                    .code(HttpStatus.NO_CONTENT.value())
                    .build();
        }

        return ProjectResponse.builder()
                .message(AppConstants.USER_RETRIEVED_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .projectList(projects)
                .build();
    }
}
