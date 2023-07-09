package com.xogito.projectmanagement.dtos.response;

import com.assessment.userprojectassignment.entity.ProjectProjection;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@SuperBuilder
@Data
public class ProjectWithoutAssignedUsersResponse extends ApiResponse{
    private Page<ProjectProjection> projectList;
    
}
