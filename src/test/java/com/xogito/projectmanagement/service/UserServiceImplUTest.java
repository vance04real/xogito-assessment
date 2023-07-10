package com.xogito.projectmanagement.service;


import com.xogito.projectmanagement.dtos.request.CreateUserRequest;
import com.xogito.projectmanagement.dtos.request.UpdateUserRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.UserResponse;
import com.xogito.projectmanagement.entity.User;
import com.xogito.projectmanagement.exceptions.NotFoundException;
import com.xogito.projectmanagement.exceptions.XogitoUserException;
import com.xogito.projectmanagement.repository.UserRepository;
import com.xogito.projectmanagement.service.impl.UserServiceImpl;
import com.xogito.projectmanagement.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplUTest {

    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;

    private final Pageable pageable = PageRequest.of(0, 10);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenRequestToCreateUser_ShouldThrowUserAlreadyExistsException() throws XogitoUserException {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("user@xogito.com");
        createUserRequest.setName("Test User");

        when(userRepository.findUserByEmailIgnoreCase(createUserRequest.getEmail()))
                .thenReturn(Optional.of(User.builder().build()));

        assertThrows(XogitoUserException.class, () -> userService.createUser(createUserRequest));
    }

    @Test
    public void givenRequestToCreateUser_ShouldReturnSuccess() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@xgito.com");
        createUserRequest.setName("Test User");

        when(userRepository.findUserByEmailIgnoreCase(createUserRequest.getEmail())).thenReturn(Optional.empty());

        ApiResponse response = userService.createUser(createUserRequest);

        assertEquals(HttpStatus.CREATED.value(), response.getCode());
        assertEquals(AppConstants.USER_CREATED_SUCCESSFUL, response.getMessage());
        verify(userRepository, times(1)).save(any(User.class));

    }

    @Test
    public void givenRequestToFindAllUsers_whenUserDoesNotExists_ShouldReturn_NoUsersFound() {
        when(userRepository.findAll(pageable)).thenReturn(Page.empty());

        UserResponse response = userService.findAllUsers(pageable);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getCode());
        assertEquals(AppConstants.NO_RESULTS_FOUND, response.getMessage());
    }



    @Test
    public void givenRequestToFindUsersByName_whenUserDoesNotExists_ShouldReturn_NoUsersFound() {
        when(userRepository.findUserByNameIgnoreCaseAndEmailIgnoreCase(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(Page.empty());

        UserResponse response = userService.searchUsers("test", "test@xogito.com", pageable);

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getCode());
        assertEquals(AppConstants.NO_RESULTS_FOUND, response.getMessage());
    }

    @Test
    public void givenRequestToDeleteUser_whenUserIsNotFound_shouldReturn_userNotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(userRepository).deleteById(anyLong());

        assertThrows(XogitoUserException.class, () -> userService.deleteUser(1L));
    }

    @Test
    public void givenRequestToDeleteUser_testDeleteUser_ShouldReturnSuccess() {
        doNothing().when(userRepository).deleteById(anyLong());

        ApiResponse response = userService.deleteUser(1L);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(AppConstants.USER_DELETE_SUCCESSFUL, response.getMessage());
    }

    @Test
    public void givenUpdate_whenUserIsNotFound_shouldReturnNotFound() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.updateUser(1L, new UpdateUserRequest()));
    }

    @Test
    public void givenUpdateUser_WhenValidRequestToUpdateUser_shouldReturnSuccess() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setEmail("updatedated@xogito.com");
        updateUserRequest.setName("Updated User");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(User.builder().build()));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        ApiResponse response = userService.updateUser(1L, updateUserRequest);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(AppConstants.USER_UPDATE_SUCCESSFUL, response.getMessage());
    }
    @Test
    public void givenRequestToFindAllUsersShouldReturnSuccess() {
        User user = User.builder()
                .email("test@xogito.com")
                .name("Test User")
                .build();


        List<User> userList = new ArrayList<>();
        userList.add(user);

        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        UserResponse response = userService.findAllUsers(pageable);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(AppConstants.USER_RETRIEVED_SUCCESSFUL, response.getMessage());
        assertNotNull(response.getUsers());
        assertEquals(1, response.getUsers().getNumberOfElements());
    }


    @Test
    public void givenValidRequest_whenSearchingUsers_shouldReturnSuccess() {
        User user = User.builder()
                .email("test@xogito.com")
                .name("Test")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user);

        Page<User> userPage = new PageImpl<>(userList);

        when(userRepository.findUserByNameIgnoreCaseAndEmailIgnoreCase(anyString(), anyString(), any(Pageable.class)))
                .thenReturn(userPage);

        UserResponse response = userService.searchUsers("Test", "test@xogito.com", pageable);

        assertEquals(HttpStatus.OK.value(), response.getCode());
        assertEquals(AppConstants.USER_RETRIEVED_SUCCESSFUL, response.getMessage());
        assertNotNull(response.getUsers());
          assertEquals(1, response.getUsers().getNumberOfElements());
    }

}
