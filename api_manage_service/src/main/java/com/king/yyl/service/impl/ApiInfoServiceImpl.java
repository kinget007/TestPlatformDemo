package com.king.yyl.service.impl;

import com.king.yyl.service.ApiInfoService;
import com.king.yyl.domain.ApiInfo;
import com.king.yyl.repository.ApiInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing ApiInfo.
 */
@Service
public class ApiInfoServiceImpl implements ApiInfoService{

    private final Logger log = LoggerFactory.getLogger(ApiInfoServiceImpl.class);

    @Inject
    private ApiInfoRepository apiInfoRepository;

    /**
     * Save a apiInfo.
     *
     * @param apiInfo the entity to save
     * @return the persisted entity
     */
    public ApiInfo save(ApiInfo apiInfo) {
        log.debug("Request to save ApiInfo : {}", apiInfo);
        ApiInfo result = apiInfoRepository.save(apiInfo);
        return result;
    }

    /**
     *  Get all the apiInfos.
     *
     *  @return the list of entities
     */
    public List<ApiInfo> findAll() {
        log.debug("Request to get all ApiInfos");
        List<ApiInfo> result = apiInfoRepository.findAll();

        return result;
    }

    /**
     *  Get one apiInfo by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ApiInfo findOne(String id) {
        log.debug("Request to get ApiInfo : {}", id);
        ApiInfo apiInfo = apiInfoRepository.findOne(id);
        return apiInfo;
    }

    /**
     *  Delete the  apiInfo by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ApiInfo : {}", id);
        apiInfoRepository.delete(id);
    }
}
