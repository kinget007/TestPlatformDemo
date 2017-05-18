package com.king.yyl.repository;

import com.king.yyl.domain.ApiInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ApiInfo entity.
 */
@SuppressWarnings("unused")
public interface ApiInfoRepository extends MongoRepository<ApiInfo,String> {

}
