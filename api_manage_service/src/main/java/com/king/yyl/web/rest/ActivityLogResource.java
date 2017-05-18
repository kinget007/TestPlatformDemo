package com.king.yyl.web.rest;

import com.king.yyl.domain.ActivityLog;
import com.king.yyl.service.ActivityLogService;
import com.king.yyl.service.dto.ActivityLogDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
public class ActivityLogResource {
    Logger logger = LoggerFactory.getLogger(ActivityLogResource.class);

    @Inject
    private ActivityLogService activityLogService;

    @RequestMapping(value = "/api/logs", method = RequestMethod.POST, headers = "Accept=application/json")
    public ActivityLog create(@RequestBody ActivityLogDTO activityLogDTO) {
        logger.debug("Creating a new activityLog with information: " + activityLogDTO);

        ActivityLog activityLog = new ActivityLog();
        activityLog.setName(activityLogDTO.getName());
        activityLog.setDescription(activityLogDTO.getDescription());
        return activityLogService.save(activityLog);
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ActivityLog delete(@PathVariable("id") String id) {
        logger.debug("Deleting activityLog with id: " + id);

        ActivityLog deleted = activityLogService.findOne(id);

        activityLogService.delete(deleted);

        return deleted;
    }

    @RequestMapping(value = "/api/logs", method = RequestMethod.GET)
    public List<ActivityLog> findAll() {
        logger.debug("Finding all logs");

        return activityLogService.findAll();
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.GET)
    public ActivityLog findById(@PathVariable("id") String id) {
        logger.debug("Finding activityLog by id: " + id);

        return activityLogService.findOne(id);
    }

    @RequestMapping(value = "/api/logs/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ActivityLog update(@PathVariable("id") Long id, @RequestBody ActivityLogDTO updated) {
        logger.debug("Updating activityLog with information: " + updated);

        ActivityLog activityLog = activityLogService.findOne(updated.getId());

        activityLog.setName(updated.getName());
        activityLog.setDescription(updated.getDescription());

        return activityLog;
    }
}
