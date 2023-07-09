package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProjectAssignmentRequest {

    private Long userId;
    private Long projectId;
}
