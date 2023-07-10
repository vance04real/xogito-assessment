package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;

@Data
@Builder
@EqualsAndHashCode
@ToString
public class UpdateUserRequest {

    private String name;

    @Email
    private String email;
}
