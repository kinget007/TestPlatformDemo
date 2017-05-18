package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.king.yyl.domain.ServerJenkins;
import com.king.yyl.service.ServerJenkinsService;
import com.king.yyl.web.rest.util.HeaderUtil;
import com.king.yyl.web.rest.util.PaginationUtil;

import com.king.yyl.web.rest.util.Ping;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ServerJenkins.
 */
@RestController
@RequestMapping("/api")
public class ServerJenkinsResource {

    private final Logger log = LoggerFactory.getLogger(ServerJenkinsResource.class);

    @Inject
    private ServerJenkinsService serverJenkinsService;

    /**
     * POST  /server-jenkins : Create a new serverJenkins.
     *
     * @param serverJenkins the serverJenkins to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serverJenkins, or with status 400 (Bad Request) if the serverJenkins has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/server-jenkins")
    @Timed
    public ResponseEntity<ServerJenkins> createServerJenkins(@RequestBody ServerJenkins serverJenkins) throws URISyntaxException {
        log.debug("REST request to save ServerJenkins : {}", serverJenkins);
        URI uri = new URI(serverJenkins.getUrlJenkins());
        if(!Ping.isHostConnectable(uri.getHost(),uri.getPort())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "connection", "Jenkins server can not connection")).body(null);
        }
        if (serverJenkins.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "idexists", "A new serverJenkins cannot already have an ID")).body(null);
        }
        ServerJenkins result = serverJenkinsService.save(serverJenkins);
        return ResponseEntity.created(new URI("/api/server-jenkins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("serverJenkins", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /server-jenkins : Updates an existing serverJenkins.
     *
     * @param serverJenkins the serverJenkins to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serverJenkins,
     * or with status 400 (Bad Request) if the serverJenkins is not valid,
     * or with status 500 (Internal Server Error) if the serverJenkins couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/server-jenkins")
    @Timed
    public ResponseEntity<ServerJenkins> updateServerJenkins(@RequestBody ServerJenkins serverJenkins) throws URISyntaxException {
        log.debug("REST request to update ServerJenkins : {}", serverJenkins);
        URI uri = new URI(serverJenkins.getUrlJenkins());
        if(!Ping.isHostConnectable(uri.getHost(),uri.getPort())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "connection", "Jenkins server can not connection")).body(null);
        }
        if (serverJenkins.getId() == null) {
            return createServerJenkins(serverJenkins);
        }
        ServerJenkins result = serverJenkinsService.save(serverJenkins);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("serverJenkins", serverJenkins.getId().toString()))
            .body(result);
    }

    /**
     * GET  /server-jenkins : get all the serverJenkins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serverJenkins in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/server-jenkins")
    @Timed
    public ResponseEntity<List<ServerJenkins>> getAllServerJenkins(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ServerJenkins");
        Page<ServerJenkins> page = serverJenkinsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/server-jenkins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /server-jenkins/:id : get the "id" serverJenkins.
     *
     * @param id the id of the serverJenkins to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serverJenkins, or with status 404 (Not Found)
     */
    @GetMapping("/server-jenkins/{id}")
    @Timed
    public ResponseEntity<ServerJenkins> getServerJenkins(@PathVariable String id) {
        log.debug("REST request to get ServerJenkins : {}", id);
        ServerJenkins serverJenkins = serverJenkinsService.findOne(id);
        return Optional.ofNullable(serverJenkins)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /server-jenkins/:id : delete the "id" serverJenkins.
     *
     * @param id the id of the serverJenkins to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/server-jenkins/{id}")
    @Timed
    public ResponseEntity<Void> deleteServerJenkins(@PathVariable String id) {
        log.debug("REST request to delete ServerJenkins : {}", id);
        serverJenkinsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("serverJenkins", id.toString())).build();
    }

}
