package com.king.yyl.repository;

import com.king.yyl.domain.Tag;
import com.king.yyl.domain.Workspace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface WorkspaceRepository extends MongoRepository<Workspace, String> {

    @Query(value = "?0")
    List<Workspace> findByQuery(String query);

    @Query("{ 'tags' : ?0 }")
    List<Workspace> findByTags(String tags);

    @Query("{ 'id' : ?0 }")
    List<Tag> findTagsByWorkspace(String workspaceId);

    List<Workspace> findByName(String name);
}
