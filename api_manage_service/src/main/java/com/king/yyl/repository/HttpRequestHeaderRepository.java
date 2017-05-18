
package com.king.yyl.repository;

import com.king.yyl.domain.parameters.HeaderParameter;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface HttpRequestHeaderRepository extends MongoRepository<HeaderParameter, String> {

}
