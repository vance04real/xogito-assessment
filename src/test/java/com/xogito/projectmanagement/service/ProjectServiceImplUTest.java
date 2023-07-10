package com.xogito.projectmanagement.service;

import com.xogito.projectmanagement.dtos.request.CreateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UpdateProjectRequest;
import com.xogito.projectmanagement.dtos.request.UserProjectAssignmentRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.ProjectResponse;
import com.xogito.projectmanagement.dtos.response.ProjectWithoutAssignedUsersResponse;
import com.xogito.projectmanagement.entity.Project;
import com.xogito.projectmanagement.entity.ProjectProjection;
import com.xogito.projectmanagement.entity.User;
import com.xogito.projectmanagement.exceptions.NotFoundException;
import com.xogito.projectmanagement.repository.ProjectRepository;
import com.xogito.projectmanagement.repository.UserRepository;
import com.xogito.projectmanagement.service.impl.ProjectServiceImpl;
import com.xogito.projectmanagement.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProjectServiceImplUTest {
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;
    private CreateProjectRequest createProjectRequest;
    private UpdateProjectRequest updateProjectRequest;
    private UserProjectAssignmentRequest userProjectAssignmentRequest;
    private Project project;
    private Page<ProjectProjection> projectProjectionPage;

    @BeforeEach
    public void setUp() {
        // Initialize test data
        createProjectRequest = new CreateProjectRequest();
        createProjectRequest.setName("Project 1");
        createProjectRequest.setDescription("Description 1");

        updateProjectRequest = new UpdateProjectRequest();
        updateProjectRequest.setName("Updated Project");
        updateProjectRequest.setDescription("Updated Description");

        userProjectAssignmentRequest = new UserProjectAssignmentRequest();
        userProjectAssignmentRequest.setUserId(1L);
        userProjectAssignmentRequest.setProjectId(1L);

        project = new Project();
        project.setId(1L);
        project.setName("Project 1");
        project.setDescription("Description 1");

    }

    @Test
    public void createProject_ShouldReturnApiResponseWithCreatedMessageAndStatusCode() {

        given(projectRepository.findProjectByNameIgnoreCase(anyString())).willReturn(Optional.empty());

        given(projectRepository.save(any(Project.class))).willReturn(project);

        ApiResponse response = projectService.createProject(createProjectRequest);

        assertThat(response.getMessage()).isEqualTo(AppConstants.PROJECT_CREATED_SUCCESSFUL);
        assertThat(response.getCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    public void findProjectsWithoutAssignedUsers_ShouldReturnProjectWithoutAssignedUsersResponse() {

        given(projectRepository.findBy(any(Pageable.class)))
                .willReturn(projectProjectionPage);

        ProjectWithoutAssignedUsersResponse response = projectService.findProjectsWithoutAssignedUsers(any(Pageable.class));

        assertThat(response).isNotNull();
    }

    @Test
    public void assignProjectToUser_ShouldThrowNotFoundException_WhenUserNotExists() {
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.assignProjectToUser(userProjectAssignmentRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User Not Found");
    }

    @Test
    public void assignProjectToUser_ShouldThrowNotFoundException_WhenProjectNotExists() {

        given(userRepository.findById(anyLong())).willReturn(Optional.of(new User()));
        given(projectRepository.findById(anyLong())).willReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.assignProjectToUser(userProjectAssignmentRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessage(AppConstants.USER_NOT_FOUND);
    }


    @Test
    public void findProjectsWithUnAssignedUsers_ShouldReturnProjectResponse() {
        // Given
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        projects.add(new Project());
        given(projectRepository.findProjectsWithNoUsersAssignedToThem(any(Pageable.class))).willReturn(new PageImpl<>(projects));

        // When
        ProjectResponse response = projectService.findProjectsWithNoUsersAssignedToThem(any(Pageable.class));

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo(AppConstants.PROJECT_RETRIEVED_SUCCESSFUL);
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    public void deleteProject_ShouldReturnApiResponseWithSuccessfulMessageAndStatusCode() {
        doNothing().when(projectRepository).deleteById(anyLong());

        ApiResponse response = projectService.deleteProject(1L);

        assertThat(response.getMessage()).isEqualTo(AppConstants.PROJECT_DELETE_SUCCESSFUL);
        assertThat(response.getCode()).isEqualTo(HttpStatus.OK.value());
    }

}

