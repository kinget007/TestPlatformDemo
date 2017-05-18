
package com.king.yyl.repository;

import com.king.yyl.domain.ApiResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface ApiResponseRepository extends MongoRepository<ApiResponse, String> {

}
