package com.xogito.projectmanagement.service;


import com.xogito.projectmanagement.dtos.request.UpdateUserRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.exceptions.NotFoundException;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
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

}
