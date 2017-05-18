package com.king.yyl.repository;

import com.king.yyl.domain.apis.custom.ScenarioDoc;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ScenarioRepository extends MongoRepository<ScenarioDoc,String> {
}
