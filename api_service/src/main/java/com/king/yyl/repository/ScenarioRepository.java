package com.king.yyl.repository;

import com.king.yyl.domain.apis.custom.ScenarioDoc;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by king.yu on 2017/1/10.
 */
public interface ScenarioRepository extends MongoRepository<ScenarioDoc,String> {
}
