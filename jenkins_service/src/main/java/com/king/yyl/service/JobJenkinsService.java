package com.king.yyl.service;

import com.king.yyl.domain.JobJenkins;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing JobJenkins.
 */
public interface JobJenkinsService {

    /**
     * Save a jobJenkins.
     *
     * @param jobJenkins the entity to save
     * @return the persisted entity
     */
    JobJenkins save(JobJenkins jobJenkins);

    /**
     *  Get all the jobJenkins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<JobJenkins> findAll(Pageable pageable);

    /**
     *  Get the "id" jobJenkins.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    JobJenkins findOne(String id);

    /**
     *  Delete the "id" jobJenkins.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
