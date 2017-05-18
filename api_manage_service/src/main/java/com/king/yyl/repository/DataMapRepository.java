
package com.king.yyl.repository;

import com.king.yyl.domain.DataMap;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface DataMapRepository extends MongoRepository<DataMap, String> {

}
