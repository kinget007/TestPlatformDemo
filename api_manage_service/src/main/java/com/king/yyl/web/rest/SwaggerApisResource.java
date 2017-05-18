package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.king.yyl.domain.SwaggerApis;
import com.king.yyl.service.SwaggerApisService;
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
 * REST controller for managing SwaggerApis.
 */
@RestController
@RequestMapping("/api")
public class SwaggerApisResource {

    private final Logger log = LoggerFactory.getLogger(SwaggerApisResource.class);

    @Inject
    private SwaggerApisService swaggerApisService;

    /**
     * POST  /swagger-apis : Create a new swaggerApis.
     *
     * @param swaggerApis the swaggerApis to create
     * @return the ResponseEntity with status 201 (Created) and with body the new swaggerApis, or with status 400 (Bad Request) if the swaggerApis has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/swagger-apis")
    @Timed
    public ResponseEntity<SwaggerApis> createSwaggerApis(@RequestBody SwaggerApis swaggerApis) throws URISyntaxException {
        log.debug("REST request to save SwaggerApis : {}", swaggerApis);
        if (swaggerApis.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("swaggerApis", "idexists", "A new swaggerApis cannot already have an ID")).body(null);
        }
        SwaggerApis result = swaggerApisService.save(swaggerApis);
        return ResponseEntity.created(new URI("/api/swagger-apis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("swaggerApis", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /swagger-apis : Updates an existing swaggerApis.
     *
     * @param swaggerApis the swaggerApis to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated swaggerApis,
     * or with status 400 (Bad Request) if the swaggerApis is not valid,
     * or with status 500 (Internal Server Error) if the swaggerApis couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/swagger-apis")
    @Timed
    public ResponseEntity<SwaggerApis> updateSwaggerApis(@RequestBody SwaggerApis swaggerApis) throws URISyntaxException {
        log.debug("REST request to update SwaggerApis : {}", swaggerApis);
        if (swaggerApis.getId() == null) {
            return createSwaggerApis(swaggerApis);
        }
        SwaggerApis result = swaggerApisService.save(swaggerApis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("swaggerApis", swaggerApis.getId().toString()))
            .body(result);
    }

    /**
     * GET  /swagger-apis : get all the swaggerApis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of swaggerApis in body
     */
    @GetMapping("/swagger-apis")
    @Timed
    public List<SwaggerApis> getAllSwaggerApis() {
        log.debug("REST request to get all SwaggerApis");
        return swaggerApisService.findAll();
    }

    /**
     * GET  /swagger-apis/:id : get the "id" swaggerApis.
     *
     * @param id the id of the swaggerApis to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the swaggerApis, or with status 404 (Not Found)
     */
    @GetMapping("/swagger-apis/{id}")
    @Timed
    public ResponseEntity<SwaggerApis> getSwaggerApis(@PathVariable String id) {
        log.debug("REST request to get SwaggerApis : {}", id);
        SwaggerApis swaggerApis = swaggerApisService.findOne(id);
        return Optional.ofNullable(swaggerApis)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /swagger-apis/:id : delete the "id" swaggerApis.
     *
     * @param id the id of the swaggerApis to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/swagger-apis/{id}")
    @Timed
    public ResponseEntity<Void> deleteSwaggerApis(@PathVariable String id) {
        log.debug("REST request to delete SwaggerApis : {}", id);
        swaggerApisService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("swaggerApis", id.toString())).build();
    }

}
