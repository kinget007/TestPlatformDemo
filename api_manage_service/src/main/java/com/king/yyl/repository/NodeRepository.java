package com.king.yyl.repository;

import com.king.yyl.domain.BaseNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface NodeRepository extends MongoRepository<BaseNode, String> {

    @Query("{ 'parentId' : ?0 }")
    List<BaseNode> getChildren(String nodeId);

    @Query("{ 'projectId' : ?0 }")
    List<BaseNode> findNodesFromAProject(String projectId);

    @Query("{ 'projectId' : ?0 ,$or : [{name : { $regex : ?1, $options: 'i' }},{ nodeType : {$exists: true}}]}")
    List<BaseNode> searchNodesFromAProject(String projectId, String search);

    @Query("{'workspaceId' : ?0, 'starred' : true , name : { $regex : ?1, $options: 'i'}}")
    Page<BaseNode> findStarredNodes(String workspaceId, String search, Pageable pageable);

    @Query("{ 'tags' : ?0 }")
    Page<BaseNode> findTaggedNodes(String tagId, Pageable pageable);

    @Query("{ 'tags' : ?0 , $or : [{name : { $regex : ?1, $options: 'i' }},{ nodeType : {$exists: true}}]}")
    Page<BaseNode> searchTaggedNodes(String tagId, String search, Pageable pageable);

    BaseNode findByName(String name);
}
