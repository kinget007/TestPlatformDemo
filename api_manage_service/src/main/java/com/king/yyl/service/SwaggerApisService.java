package com.king.yyl.service;

import com.king.yyl.domain.SwaggerApis;
import java.util.List;

/**
 * Service Interface for managing SwaggerApis.
 */
public interface SwaggerApisService {

    /**
     * Save a swaggerApis.
     *
     * @param swaggerApis the entity to save
     * @return the persisted entity
     */
    SwaggerApis save(SwaggerApis swaggerApis);

    /**
     *  Get all the swaggerApis.
     *
     *  @return the list of entities
     */
    List<SwaggerApis> findAll();

    /**
     *  Get the "id" swaggerApis.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SwaggerApis findOne(String id);

    /**
     *  Delete the "id" swaggerApis.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
