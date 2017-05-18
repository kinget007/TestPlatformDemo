package com.king.yyl.service;

import com.king.yyl.domain.ActivityLog;

import java.util.List;


public interface ActivityLogService {

    ActivityLog save(ActivityLog activityLog);

    void delete(ActivityLog activityLog);

    ActivityLog findOne(String id);

    List<ActivityLog> findAll();
}
