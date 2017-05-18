
package com.king.yyl.repository;

import com.king.yyl.domain.Config;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface ConfigRepository extends MongoRepository<Config, String> {

}
