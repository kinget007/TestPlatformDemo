package com.king.yyl.repository;

import com.king.yyl.domain.ServerJenkins;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ServerJenkins entity.
 */
@SuppressWarnings("unused")
public interface ServerJenkinsRepository extends MongoRepository<ServerJenkins,String> {

}
