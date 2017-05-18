package com.king.yyl.service.impl;

import com.king.yyl.service.JobJenkinsService;
import com.king.yyl.domain.JobJenkins;
import com.king.yyl.repository.JobJenkinsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing JobJenkins.
 */
@Service
public class JobJenkinsServiceImpl implements JobJenkinsService{

    private final Logger log = LoggerFactory.getLogger(JobJenkinsServiceImpl.class);

    @Inject
    private JobJenkinsRepository jobJenkinsRepository;

    /**
     * Save a jobJenkins.
     *
     * @param jobJenkins the entity to save
     * @return the persisted entity
     */
    public JobJenkins save(JobJenkins jobJenkins) {
        log.debug("Request to save JobJenkins : {}", jobJenkins);
        JobJenkins result = jobJenkinsRepository.save(jobJenkins);
        return result;
    }

    /**
     *  Get all the jobJenkins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<JobJenkins> findAll(Pageable pageable) {
        log.debug("Request to get all JobJenkins");
        Page<JobJenkins> result = jobJenkinsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one jobJenkins by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public JobJenkins findOne(String id) {
        log.debug("Request to get JobJenkins : {}", id);
        JobJenkins jobJenkins = jobJenkinsRepository.findOne(id);
        return jobJenkins;
    }

    /**
     *  Delete the  jobJenkins by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete JobJenkins : {}", id);
        jobJenkinsRepository.delete(id);
    }
}
