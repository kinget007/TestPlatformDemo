package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.king.yyl.domain.TestSuit;
import com.king.yyl.service.TestSuitService;
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
 * REST controller for managing TestSuit.
 */
@RestController
@RequestMapping("/api")
public class TestSuitResource {

    private final Logger log = LoggerFactory.getLogger(TestSuitResource.class);

    @Inject
    private TestSuitService testSuitService;

    /**
     * POST  /test-suits : Create a new testSuit.
     *
     * @param testSuit the testSuit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new testSuit, or with status 400 (Bad Request) if the testSuit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/test-suits")
    @Timed
    public ResponseEntity<TestSuit> createTestSuit(@RequestBody TestSuit testSuit) throws URISyntaxException {
        log.debug("REST request to save TestSuit : {}", testSuit);
        if (testSuit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("testSuit", "idexists", "A new testSuit cannot already have an ID")).body(null);
        }
        TestSuit result = testSuitService.save(testSuit);
        return ResponseEntity.created(new URI("/api/test-suits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("testSuit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /test-suits : Updates an existing testSuit.
     *
     * @param testSuit the testSuit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated testSuit,
     * or with status 400 (Bad Request) if the testSuit is not valid,
     * or with status 500 (Internal Server Error) if the testSuit couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/test-suits")
    @Timed
    public ResponseEntity<TestSuit> updateTestSuit(@RequestBody TestSuit testSuit) throws URISyntaxException {
        log.debug("REST request to update TestSuit : {}", testSuit);
        if (testSuit.getId() == null) {
            return createTestSuit(testSuit);
        }
        TestSuit result = testSuitService.save(testSuit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("testSuit", testSuit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /test-suits : get all the testSuits.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of testSuits in body
     */
    @GetMapping("/test-suits")
    @Timed
    public List<TestSuit> getAllTestSuits() {
        log.debug("REST request to get all TestSuits");
        return testSuitService.findAll();
    }

    /**
     * GET  /test-suits/:id : get the "id" testSuit.
     *
     * @param id the id of the testSuit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the testSuit, or with status 404 (Not Found)
     */
    @GetMapping("/test-suits/{id}")
    @Timed
    public ResponseEntity<TestSuit> getTestSuit(@PathVariable String id) {
        log.debug("REST request to get TestSuit : {}", id);
        TestSuit testSuit = testSuitService.findOne(id);
        return Optional.ofNullable(testSuit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /test-suits/:id : delete the "id" testSuit.
     *
     * @param id the id of the testSuit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/test-suits/{id}")
    @Timed
    public ResponseEntity<Void> deleteTestSuit(@PathVariable String id) {
        log.debug("REST request to delete TestSuit : {}", id);
        testSuitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("testSuit", id.toString())).build();
    }

}
