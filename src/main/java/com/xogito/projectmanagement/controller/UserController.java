package com.xogito.projectmanagement.controller;

import com.xogito.projectmanagement.dtos.request.CreateUserRequest;
import com.xogito.projectmanagement.dtos.request.UpdateUserRequest;
import com.xogito.projectmanagement.dtos.response.ApiResponse;
import com.xogito.projectmanagement.dtos.response.UserResponse;
import com.xogito.projectmanagement.service.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        ApiResponse apiResponse = userService.createUser(createUserRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {

        ApiResponse apiResponse = userService.deleteUser(id);

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/search")
    public ResponseEntity<UserResponse> searchUser(@RequestParam String name, @RequestParam  String email, @RequestParam(defaultValue = "0", required = false)
    Integer page,
                                                   @RequestParam(defaultValue = "5", required = false)
                                                   Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        UserResponse userResponse = userService.searchUsers(name , email, paging);

        return ResponseEntity.ok(userResponse);

    }


    @GetMapping("/retrieveUsers")
    public ResponseEntity<UserResponse> retrieveUsers(@RequestParam(defaultValue = "0", required = false)
    Integer page,
                                                   @RequestParam(defaultValue = "5", required = false)
                                                   Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        UserResponse userResponse = userService.findAllUsers(paging);

        return ResponseEntity.ok(userResponse);

    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> retrieveProject(@PathVariable Long id) {
        UserResponse apiResponse = userService.findUserById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        ApiResponse apiResponse = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(apiResponse);
    }
}
