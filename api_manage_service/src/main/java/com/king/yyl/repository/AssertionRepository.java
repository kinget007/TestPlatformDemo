package com.king.yyl.repository;

import com.king.yyl.domain.Assertion;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface AssertionRepository extends MongoRepository<Assertion, String> {

}



