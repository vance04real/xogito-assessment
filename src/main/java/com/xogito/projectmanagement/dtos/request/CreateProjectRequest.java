package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectRequest {

    private String name;
    private String description;
}
