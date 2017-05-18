package com.king.yyl.repository;

import com.king.yyl.domain.Project;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("unused")
public interface ProjectRepository extends MongoRepository<Project, String> {

    @Query("{ 'workspaceId' : '' }")
    List<Project> findProjectsFromAWorkspace(@Param("workspaceId") String workspaceId);

    @Query(value="{'workspaceId':?0}")
    List<Project> findProjectsByWorkspaceId(String workspaceId);
}
