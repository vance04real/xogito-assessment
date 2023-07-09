package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProjectRequest {
    private String name;
    private String description;
}
