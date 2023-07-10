package com.xogito.projectmanagement.dtos.response;

import com.xogito.projectmanagement.entity.ProjectProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectWithoutAssignedUsersResponse extends ApiResponse{

    private Page<ProjectProjection> projectList;
    
}
