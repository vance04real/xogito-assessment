package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchUserRequest {

    private String name;

    private String email;
}
