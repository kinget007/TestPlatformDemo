package com.king.yyl.service.impl;

import com.king.yyl.service.TestSuitService;
import com.king.yyl.domain.TestSuit;
import com.king.yyl.repository.TestSuitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing TestSuit.
 */
@Service
public class TestSuitServiceImpl implements TestSuitService{

    private final Logger log = LoggerFactory.getLogger(TestSuitServiceImpl.class);

    @Inject
    private TestSuitRepository testSuitRepository;

    /**
     * Save a testSuit.
     *
     * @param testSuit the entity to save
     * @return the persisted entity
     */
    public TestSuit save(TestSuit testSuit) {
        log.debug("Request to save TestSuit : {}", testSuit);
        TestSuit result = testSuitRepository.save(testSuit);
        return result;
    }

    /**
     *  Get all the testSuits.
     *
     *  @return the list of entities
     */
    public List<TestSuit> findAll() {
        log.debug("Request to get all TestSuits");
        List<TestSuit> result = testSuitRepository.findAll();

        return result;
    }

    /**
     *  Get one testSuit by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public TestSuit findOne(String id) {
        log.debug("Request to get TestSuit : {}", id);
        TestSuit testSuit = testSuitRepository.findOne(id);
        return testSuit;
    }

    /**
     *  Delete the  testSuit by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete TestSuit : {}", id);
        testSuitRepository.delete(id);
    }
}
