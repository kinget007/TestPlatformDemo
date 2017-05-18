package com.king.yyl.repository;

import com.king.yyl.domain.GenericEntityData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

@SuppressWarnings("unused")
public interface GenericEntityDataRepository extends MongoRepository<GenericEntityData, String> {

    @Query("{ 'name' : ?0 }")
    List<GenericEntityData> findEntityDataByName(String entityName);
}
