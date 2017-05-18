package com.king.yyl.repository;

import com.king.yyl.domain.GenericEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

@SuppressWarnings("unused")
public interface GenericEntityRepository extends MongoRepository<GenericEntity, String> {

    @Query("{ 'name' : ?0 }")
    GenericEntity findEntityByName(String entityName);
}
