package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class CreateUserRequest {
    @NotNull
    private String name;
    @NotNull
    @Email
    private String email;
}
