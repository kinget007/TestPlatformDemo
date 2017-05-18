package com.king.yyl.service.impl;

import com.king.yyl.service.SwaggerApisService;
import com.king.yyl.domain.SwaggerApis;
import com.king.yyl.repository.SwaggerApisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SwaggerApis.
 */
@Service
public class SwaggerApisServiceImpl implements SwaggerApisService{

    private final Logger log = LoggerFactory.getLogger(SwaggerApisServiceImpl.class);

    @Inject
    private SwaggerApisRepository swaggerApisRepository;

    /**
     * Save a swaggerApis.
     *
     * @param swaggerApis the entity to save
     * @return the persisted entity
     */
    public SwaggerApis save(SwaggerApis swaggerApis) {
        log.debug("Request to save SwaggerApis : {}", swaggerApis);
        SwaggerApis result = swaggerApisRepository.save(swaggerApis);
        return result;
    }

    /**
     *  Get all the swaggerApis.
     *
     *  @return the list of entities
     */
    public List<SwaggerApis> findAll() {
        log.debug("Request to get all SwaggerApis");
        List<SwaggerApis> result = swaggerApisRepository.findAll();

        return result;
    }

    /**
     *  Get one swaggerApis by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public SwaggerApis findOne(String id) {
        log.debug("Request to get SwaggerApis : {}", id);
        SwaggerApis swaggerApis = swaggerApisRepository.findOne(id);
        return swaggerApis;
    }

    /**
     *  Delete the  swaggerApis by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete SwaggerApis : {}", id);
        swaggerApisRepository.delete(id);
    }
}
