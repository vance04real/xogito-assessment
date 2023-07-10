package com.xogito.projectmanagement.dtos.request;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode
@ToString
public class UserProjectAssignmentRequest {

    private Long userId;

    private Long projectId;
}
