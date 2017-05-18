package com.king.yyl.repository;

import com.king.yyl.domain.Environment;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface EnvironmentRepository extends MongoRepository<Environment, String> {

}
