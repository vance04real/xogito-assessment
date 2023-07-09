package com.xogito.projectmanagement.dtos.response;

import com.assessment.userprojectassignment.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@Data
@SuperBuilder
public class UserResponse extends ApiResponse{
    Page<User> users;

}
