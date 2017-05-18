package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.king.yyl.domain.ApiInfo;
import com.king.yyl.service.ApiInfoService;
import com.king.yyl.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ApiInfo.
 */
@RestController
@RequestMapping("/api")
public class ApiInfoResource {

    private final Logger log = LoggerFactory.getLogger(ApiInfoResource.class);

    @Inject
    private ApiInfoService apiInfoService;

    /**
     * POST  /api-infos : Create a new apiInfo.
     *
     * @param apiInfo the apiInfo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new apiInfo, or with status 400 (Bad Request) if the apiInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/api-infos")
    @Timed
    public ResponseEntity<ApiInfo> createApiInfo(@RequestBody ApiInfo apiInfo) throws URISyntaxException {
        log.debug("REST request to save ApiInfo : {}", apiInfo);
        if (apiInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("apiInfo", "idexists", "A new apiInfo cannot already have an ID")).body(null);
        }
        ApiInfo result = apiInfoService.save(apiInfo);
        return ResponseEntity.created(new URI("/api/api-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("apiInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /api-infos : Updates an existing apiInfo.
     *
     * @param apiInfo the apiInfo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated apiInfo,
     * or with status 400 (Bad Request) if the apiInfo is not valid,
     * or with status 500 (Internal Server Error) if the apiInfo couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/api-infos")
    @Timed
    public ResponseEntity<ApiInfo> updateApiInfo(@RequestBody ApiInfo apiInfo) throws URISyntaxException {
        log.debug("REST request to update ApiInfo : {}", apiInfo);
        if (apiInfo.getId() == null) {
            return createApiInfo(apiInfo);
        }
        ApiInfo result = apiInfoService.save(apiInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apiInfo", apiInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /api-infos : get all the apiInfos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of apiInfos in body
     */
    @GetMapping("/api-infos")
    @Timed
    public List<ApiInfo> getAllApiInfos() {
        log.debug("REST request to get all ApiInfos");
        return apiInfoService.findAll();
    }

    /**
     * GET  /api-infos/:id : get the "id" apiInfo.
     *
     * @param id the id of the apiInfo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the apiInfo, or with status 404 (Not Found)
     */
    @GetMapping("/api-infos/{id}")
    @Timed
    public ResponseEntity<ApiInfo> getApiInfo(@PathVariable String id) {
        log.debug("REST request to get ApiInfo : {}", id);
        ApiInfo apiInfo = apiInfoService.findOne(id);
        return Optional.ofNullable(apiInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /api-infos/:id : delete the "id" apiInfo.
     *
     * @param id the id of the apiInfo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/api-infos/{id}")
    @Timed
    public ResponseEntity<Void> deleteApiInfo(@PathVariable String id) {
        log.debug("REST request to delete ApiInfo : {}", id);
        apiInfoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("apiInfo", id.toString())).build();
    }

}
