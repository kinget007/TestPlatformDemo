package com.king.yyl.repository;

import com.king.yyl.domain.Module;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface ModuleRepository extends MongoRepository<Module, String> {

}
