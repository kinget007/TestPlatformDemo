package com.king.yyl.repository;

import com.king.yyl.domain.Conversation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@SuppressWarnings("unused")
public interface ConversationRepository extends MongoRepository<Conversation, String> {

    @Query("{ 'workspaceId' : ?0 }")
    Page<Conversation> findConversationsFromWorkspace(String workspaceId, Pageable pageable);

    @Query("{ 'workspaceId' : ?0 ,name : { $regex : ?1, $options: 'i' }}")
    Page<Conversation> findConversationsFromWorkspaceByName(String workspaceId, String name, Pageable pageable);

    @Query("{'name' : ?0}")
    Conversation findByName(String name);

}
