package com.xogito.projectmanagement.service.impl;

import com.xogito.projectmanagement.dtos.request.CreateUserRequest;
import com.xogito.projectmanagement.dtos.request.UpdateUserRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.UserResponse;
import com.xogito.projectmanagement.entity.User;
import com.xogito.projectmanagement.exceptions.NotFoundException;
import com.xogito.projectmanagement.exceptions.XogitoUserException;
import com.xogito.projectmanagement.repository.UserRepository;
import com.xogito.projectmanagement.service.api.UserService;
import com.xogito.projectmanagement.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public ApiResponse createUser(CreateUserRequest createUserRequest) {

        var retrievedUser = userRepository.findUserByEmailIgnoreCase(createUserRequest.getEmail());
        if(retrievedUser.isPresent()){
            throw new XogitoUserException(AppConstants.USER_ALREADY_EXISTS);
        }
        userRepository.save(User.builder()
                        .email(createUserRequest.getEmail())
                        .name(createUserRequest.getName())
                        .build());

        return ApiResponse.builder()
                .message(AppConstants.USER_CREATED_SUCCESSFUL)
                .code(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public UserResponse findAllUsers(Pageable page) {

        var users = userRepository.findAll(page);

        if(users.getTotalElements() == 0){
                return UserResponse.builder()
                        .message(AppConstants.NO_RESULTS_FOUND)
                        .code(HttpStatus.NO_CONTENT.value())
                        .build();
            }

        return UserResponse.builder()
                .message(AppConstants.USER_RETRIEVED_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .users(users)
                .build();
    }

    @Override
    public UserResponse searchUsers(String name, String email, Pageable pageable) {
       var retrievedUsers = userRepository.findUserByNameAndEmailIgnoreCase(name, email, pageable);
       if(retrievedUsers.getTotalElements() == 0){
           return UserResponse.builder()
                   .message(AppConstants.NO_RESULTS_FOUND)
                   .code(HttpStatus.NO_CONTENT.value())
                   .build();
       }
        return UserResponse.builder()
                .message(AppConstants.USER_RETRIEVED_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .users(retrievedUsers)
                .build();
    }

    @Override
    public ApiResponse deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            log.error("Error occurred on deletion {}", ex.getMessage());
            throw new XogitoUserException(AppConstants.USER_TO_BE_DELETED_NOT_FOUND);
        }

        return ApiResponse.builder()
                .message(AppConstants.USER_DELETE_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public ApiResponse updateUser(Long id, UpdateUserRequest updateUserRequest) {

        var user = userRepository.findById(id).orElseThrow(()-> new NotFoundException(AppConstants.USER_NOT_FOUND));

        if (Objects.nonNull(updateUserRequest.getName()) && !"".equalsIgnoreCase(updateUserRequest.getName())) {

            user.setName(updateUserRequest.getName());
        }

        if (Objects.nonNull(updateUserRequest.getEmail()) && !"".equalsIgnoreCase(updateUserRequest.getEmail())) {

            user.setEmail(updateUserRequest.getEmail());
        }

       userRepository.save(user);

        return ApiResponse.builder()
                .message(AppConstants.USER_UPDATE_SUCCESSFUL)
                .code(HttpStatus.OK.value())
                .build();
    }
}
