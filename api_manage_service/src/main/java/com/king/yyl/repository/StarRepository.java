
package com.king.yyl.repository;

import com.king.yyl.domain.Star;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface StarRepository extends MongoRepository<Star, String> {

}
