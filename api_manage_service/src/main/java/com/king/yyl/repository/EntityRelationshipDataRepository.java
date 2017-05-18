package com.king.yyl.repository;

import com.king.yyl.domain.EntityRelationshipData;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface EntityRelationshipDataRepository extends MongoRepository<EntityRelationshipData, String> {

}
