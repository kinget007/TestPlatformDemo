package com.king.yyl.repository;

import com.king.yyl.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@SuppressWarnings("unused")
public interface TagRepository extends MongoRepository<Tag, String> {

    @Query("{ 'workspaceId' : '' }")
    List<Tag> findTagsFromAWorkspace(@Param("workspaceId") String workspaceId);

}
