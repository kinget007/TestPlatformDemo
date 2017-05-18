package com.king.yyl.service;

import com.king.yyl.domain.ApiInfo;
import java.util.List;

/**
 * Service Interface for managing ApiInfo.
 */
public interface ApiInfoService {

    /**
     * Save a apiInfo.
     *
     * @param apiInfo the entity to save
     * @return the persisted entity
     */
    ApiInfo save(ApiInfo apiInfo);

    /**
     *  Get all the apiInfos.
     *
     *  @return the list of entities
     */
    List<ApiInfo> findAll();

    /**
     *  Get the "id" apiInfo.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ApiInfo findOne(String id);

    /**
     *  Delete the "id" apiInfo.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
