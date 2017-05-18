package com.king.yyl.service.impl;

import com.king.yyl.service.ServerJenkinsService;
import com.king.yyl.domain.ServerJenkins;
import com.king.yyl.repository.ServerJenkinsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Service Implementation for managing ServerJenkins.
 */
@Service
public class ServerJenkinsServiceImpl implements ServerJenkinsService{

    private final Logger log = LoggerFactory.getLogger(ServerJenkinsServiceImpl.class);

    @Inject
    private ServerJenkinsRepository serverJenkinsRepository;

    /**
     * Save a serverJenkins.
     *
     * @param serverJenkins the entity to save
     * @return the persisted entity
     */
    public ServerJenkins save(ServerJenkins serverJenkins) {
        log.debug("Request to save ServerJenkins : {}", serverJenkins);
        ServerJenkins result = serverJenkinsRepository.save(serverJenkins);
        return result;
    }

    /**
     *  Get all the serverJenkins.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    public Page<ServerJenkins> findAll(Pageable pageable) {
        log.debug("Request to get all ServerJenkins");
        Page<ServerJenkins> result = serverJenkinsRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one serverJenkins by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public ServerJenkins findOne(String id) {
        log.debug("Request to get ServerJenkins : {}", id);
        ServerJenkins serverJenkins = serverJenkinsRepository.findOne(id);
        return serverJenkins;
    }

    /**
     *  Delete the  serverJenkins by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete ServerJenkins : {}", id);
        serverJenkinsRepository.delete(id);
    }
}
