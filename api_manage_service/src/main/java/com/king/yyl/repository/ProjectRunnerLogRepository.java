package com.king.yyl.repository;

import com.king.yyl.domain.ProjectRunnerLog;
import org.springframework.data.mongodb.repository.MongoRepository;

@SuppressWarnings("unused")
public interface ProjectRunnerLogRepository extends MongoRepository<ProjectRunnerLog, String> {

}
