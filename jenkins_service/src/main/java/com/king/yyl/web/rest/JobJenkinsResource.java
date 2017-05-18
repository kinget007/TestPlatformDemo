package com.king.yyl.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.king.yyl.domain.JobJenkins;
import com.king.yyl.domain.ServerJenkins;
import com.king.yyl.service.JobJenkinsService;
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
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing JobJenkins.
 */
@RestController
@RequestMapping("/api")
public class JobJenkinsResource {

    private final Logger log = LoggerFactory.getLogger(JobJenkinsResource.class);

    @Inject
    private JobJenkinsService jobJenkinsService;

    @Inject
    private ServerJenkinsService serverJenkinsService;

    /**
     * POST  /job-jenkins : Create a new jobJenkins.
     *
     * @param jobJenkins the jobJenkins to create
     * @return the ResponseEntity with status 201 (Created) and with body the new jobJenkins, or with status 400 (Bad Request) if the jobJenkins has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/job-jenkins")
    @Timed
    public ResponseEntity<JobJenkins> createJobJenkins(@RequestBody JobJenkins jobJenkins) throws URISyntaxException, IOException {
        log.debug("REST request to save JobJenkins : {}", jobJenkins);
        ServerJenkins serverJenkins = serverJenkinsService.findOne(jobJenkins.getServerIdJenkins());
        URI uri = new URI(serverJenkins.getUrlJenkins());
        if(!Ping.isHostConnectable(uri.getHost(),uri.getPort())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "connectionServer", "Jenkins server can not connection")).body(null);
        }
        if (jobJenkins.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("jobJenkins", "idexists", "A new jobJenkins cannot already have an ID")).body(null);
        }
        JenkinsServer server = new JenkinsServer(uri,serverJenkins.getLoginJenkins(),serverJenkins.getPwdJenkins());
        String xmlString = "<?xml version='1.0' encoding='UTF-8'?>\n" +
            "<project>\n" +
            "  <description>AutoCreate</description>\n" +
            "  <keepDependencies>false</keepDependencies>\n" +
            "  <properties/>\n" +
            "  <scm class=\"hudson.scm.NullSCM\"/>\n" +
            "  <canRoam>true</canRoam>\n" +
            "  <disabled>false</disabled>\n" +
            "  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>\n" +
            "  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>\n" +
            "  <triggers/>\n" +
            "  <concurrentBuild>false</concurrentBuild>\n" +
            "  <builders>\n" +
            "    <hudson.tasks.Shell>\n" +
            "      <command>env </command>\n" +
            "    </hudson.tasks.Shell>\n" +
            "  </builders>\n" +
            "  <publishers/>\n" +
            "  <buildWrappers/>\n" +
            "</project>";
        server.createJob(jobJenkins.getDescription(),xmlString,true);
        JobWithDetails jobWithDetails = server.getJob(jobJenkins.getDescription());

        if(jobWithDetails.equals(null)){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "createJob", "Jenkins Job create failed")).body(null);
        }

        jobJenkins.setUrlJenkins(jobWithDetails.getUrl());
        JobJenkins result = jobJenkinsService.save(jobJenkins);
        return ResponseEntity.created(new URI("/api/job-jenkins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jobJenkins", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /job-jenkins : Updates an existing jobJenkins.
     *
     * @param jobJenkins the jobJenkins to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated jobJenkins,
     * or with status 400 (Bad Request) if the jobJenkins is not valid,
     * or with status 500 (Internal Server Error) if the jobJenkins couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/job-jenkins")
    @Timed
    public ResponseEntity<JobJenkins> updateJobJenkins(@RequestBody JobJenkins jobJenkins) throws URISyntaxException, IOException {
        log.debug("REST request to update JobJenkins : {}", jobJenkins);
        if (jobJenkins.getId() == null) {
            return createJobJenkins(jobJenkins);
        }
        JobJenkins result = jobJenkinsService.save(jobJenkins);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jobJenkins", jobJenkins.getId().toString()))
            .body(result);
    }

    /**
     * GET  /job-jenkins : get all the jobJenkins.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of jobJenkins in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/job-jenkins")
    @Timed
    public ResponseEntity<List<JobJenkins>> getAllJobJenkins(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of JobJenkins");
        Page<JobJenkins> page = jobJenkinsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/job-jenkins");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /job-jenkins/:id : get the "id" jobJenkins.
     *
     * @param id the id of the jobJenkins to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobJenkins, or with status 404 (Not Found)
     */
    @GetMapping("/job-jenkins/{id}")
    @Timed
    public ResponseEntity<JobJenkins> getJobJenkins(@PathVariable String id) {
        log.debug("REST request to get JobJenkins : {}", id);
        JobJenkins jobJenkins = jobJenkinsService.findOne(id);
        return Optional.ofNullable(jobJenkins)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /job-jenkins/:id : delete the "id" jobJenkins.
     *
     * @param id the id of the jobJenkins to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/job-jenkins/{id}")
    @Timed
    public ResponseEntity<Void> deleteJobJenkins(@PathVariable String id) throws URISyntaxException, IOException {
        log.debug("REST request to delete JobJenkins : {}", id);
        JobJenkins jobJenkins = jobJenkinsService.findOne(id);
        ServerJenkins serverJenkins = serverJenkinsService.findOne(jobJenkins.getServerIdJenkins());
        URI uri = new URI(serverJenkins.getUrlJenkins());
        if(!Ping.isHostConnectable(uri.getHost(),uri.getPort())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "connectionServer", "Jenkins server can not connection")).body(null);
        }
        JenkinsServer server = new JenkinsServer(uri,serverJenkins.getLoginJenkins(),serverJenkins.getPwdJenkins());
        server.deleteJob(jobJenkins.getDescription(),true);
        jobJenkinsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jobJenkins", id.toString())).build();
    }


    /**
     * GET  /job-jenkins/:id : get the "id" jobJenkins.
     *
     * @param id the id of the jobJenkins to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the jobJenkins, or with status 404 (Not Found)
     */
    @GetMapping("/jenkins-job/{id}")
    @Timed
    public ResponseEntity<JobJenkins> getJenkinsJob(@PathVariable String id) throws URISyntaxException, IOException {
        log.debug("REST request to get JobJenkins : {}", id);
        JobJenkins jobJenkins = jobJenkinsService.findOne(id);
        ServerJenkins serverJenkins = serverJenkinsService.findOne(jobJenkins.getServerIdJenkins());
        URI uri = new URI(serverJenkins.getUrlJenkins());
        if(!Ping.isHostConnectable(uri.getHost(),uri.getPort())){
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("serverJenkins", "connectionServer", "Jenkins server can not connection")).body(null);
        }
        JenkinsServer server = new JenkinsServer(uri,serverJenkins.getLoginJenkins(),serverJenkins.getPwdJenkins());
        server.getJob(jobJenkins.getDescription()).build(true);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createAlert("Jenkins Job Build Success", jobJenkins.getId().toString()))
            .body(jobJenkins);
    }

}
