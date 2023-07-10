package com.xogito.projectmanagement.repository;


import com.xogito.projectmanagement.entity.Project;
import com.xogito.projectmanagement.entity.ProjectProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository <Project, Long >{
    Page<Project> findProjectByNameIgnoreCase(String name, Pageable pageable);
    Optional<Project> findProjectByNameIgnoreCase(String name);
    Page<ProjectProjection> findBy(Pageable pageable);
    Page<Project> findProjectById(Long id, Pageable pageable);

    @Query("SELECT p FROM Project p LEFT JOIN p.assignedUsers u WHERE u IS NULL")
    Page<Project> findProjectsWithNoUsersAssignedToThem(Pageable pageable);

}
