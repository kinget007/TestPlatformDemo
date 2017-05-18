package com.king.yyl.repository;

import com.king.yyl.domain.apis.custom.ApiDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApiDocRepository extends MongoRepository<ApiDoc,String> {

}
