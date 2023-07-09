package com.xogito.projectmanagement.dtos.response;

import com.assessment.userprojectassignment.entity.Project;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@SuperBuilder
@Data
public class ProjectResponse extends ApiResponse{
    private Page<Project> projectList;

}
