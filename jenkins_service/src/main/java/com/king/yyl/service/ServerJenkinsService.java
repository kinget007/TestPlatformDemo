package com.king.yyl.service;

import com.king.yyl.domain.ServerJenkins;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ServerJenkins.
 */
public interface ServerJenkinsService {

    /**
     * Save a serverJenkins.
     *
     * @param serverJenkins the entity to save
     * @return the persisted entity
     */
    ServerJenkins save(ServerJenkins serverJenkins);

    /**
     *  Get all the serverJenkins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ServerJenkins> findAll(Pageable pageable);

    /**
     *  Get the "id" serverJenkins.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ServerJenkins findOne(String id);

    /**
     *  Delete the "id" serverJenkins.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
