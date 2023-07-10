package com.xogito.projectmanagement.service.api;

import com.xogito.projectmanagement.dtos.request.CreateUserRequest;
import com.xogito.projectmanagement.dtos.request.UpdateUserRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.UserResponse;
import org.springframework.data.domain.Pageable;

public interface UserService {
    //Create , Put, Get , Delete , Search
  ApiResponse createUser(CreateUserRequest createUserRequest);
  UserResponse findAllUsers(Pageable pageable);
  UserResponse searchUsers(String name, String email, Pageable pageable);
  ApiResponse deleteUser(Long id);
  ApiResponse updateUser(Long id, UpdateUserRequest updateUserRequest);

}
