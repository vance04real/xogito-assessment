package com.xogito.projectmanagement.dtos.response;


import com.xogito.projectmanagement.entity.Project;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Page;

@SuperBuilder
@Data
public class ProjectResponse extends ApiResponse{

    Page<Project> projectList;

}
