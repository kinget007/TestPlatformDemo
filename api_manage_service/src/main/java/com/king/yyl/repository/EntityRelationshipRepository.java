package com.king.yyl.repository;

import com.king.yyl.domain.EntityRelationship;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface EntityRelationshipRepository extends MongoRepository<EntityRelationship, String> {

}
