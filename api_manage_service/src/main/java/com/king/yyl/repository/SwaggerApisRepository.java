package com.king.yyl.repository;

import com.king.yyl.domain.SwaggerApis;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the SwaggerApis entity.
 */
@SuppressWarnings("unused")
public interface SwaggerApisRepository extends MongoRepository<SwaggerApis,String> {

}
