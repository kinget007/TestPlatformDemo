package com.king.yyl.web.rest;

import com.king.yyl.domain.ApiRequest;
import com.king.yyl.domain.Assertion;
import com.king.yyl.domain.BaseNode;
import com.king.yyl.domain.BodyAssert;
import com.king.yyl.repository.ApiRequestRepository;
import com.king.yyl.repository.AssertionRepository;
import com.king.yyl.repository.NodeRepository;
import com.king.yyl.service.dto.AssertionDTO;
import com.king.yyl.service.dto.BodyAssertDTO;
import com.king.yyl.web.rest.util.EntityToDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AssertionResource {
    Logger logger = LoggerFactory.getLogger(AssertionResource.class);

    @Inject
    private NodeRepository nodeRepository;

    @Inject
    private ApiRequestRepository apiRequestRepository;

    @Inject
    private AssertionRepository assertionRepository;

    @RequestMapping(value="/requests/{nodeId}/asserts", method = RequestMethod.POST, headers = "Accept=application/json")
    public Assertion save(@PathVariable("nodeId") String nodeId, @RequestBody AssertionDTO assertionDTO) {

        BaseNode node = nodeRepository.findOne(nodeId);
        if(node == null || node.getConversation() == null){
            return null;
        }

        ApiRequest apiRequest = node.getConversation().getApiRequest();
        if (apiRequest == null) {
            return null;
        }

        Assertion assertion = new Assertion();
        List<BodyAssertDTO> bodyAssertDTOs = assertionDTO.getBodyAssertDTOs();

        if (bodyAssertDTOs != null && !bodyAssertDTOs.isEmpty()) {
            List<BodyAssert> bodyAsserts = new ArrayList<BodyAssert>();
            BodyAssert bodyAssert = null;
            for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
                bodyAssert = new BodyAssert();
                bodyAssert.setPropertyName(bodyAssertDTO.getPropertyName());
                bodyAssert.setComparator(bodyAssertDTO.getComparator());
                bodyAssert.setExpectedValue(bodyAssertDTO.getExpectedValue());
                bodyAsserts.add(bodyAssert);
            }
            assertion.setBodyAsserts(bodyAsserts);
            apiRequest.setAssertion(assertion);
        }

        assertion = assertionRepository.save(assertion);

        ApiRequest savedRFRequest = apiRequestRepository.save(apiRequest);
        return savedRFRequest.getAssertion();
    }

    @RequestMapping(value="/requests/{nodeId}/asserts", method = RequestMethod.GET)
    public Assertion findAsserts(@PathVariable("nodeId") String nodeId) {
        BaseNode node = nodeRepository.findOne(nodeId);
        if(node == null || node.getConversation() == null){
            return null;
        }

        ApiRequest apiRequest = node.getConversation().getApiRequest();
        if (apiRequest == null) {
            return null;
        }

        Assertion assertion = apiRequest.getAssertion();
        return assertion;
    }

    @RequestMapping(value="/requests/{nodeId}/assertiondto", method = RequestMethod.GET)
    public AssertionDTO getSavedAsserts(@PathVariable("nodeId") String nodeId) {
        BaseNode node = nodeRepository.findOne(nodeId);
        if(node == null || node.getConversation() == null){
            return null;
        }

        ApiRequest rfRequest = node.getConversation().getApiRequest();
        if (rfRequest == null) {
            return null;
        }

        Assertion assertion = rfRequest.getAssertion();
        AssertionDTO assertiondto = EntityToDTO.toDTO(assertion);
        return assertiondto;
    }

    @RequestMapping(value="/requests/{nodeId}/assertiondto", method = RequestMethod.PUT, headers = "Accept=application/json")
    public void update(@PathVariable("nodeId") String nodeId, @RequestBody AssertionDTO assertionDTO) {
        BaseNode node = nodeRepository.findOne(nodeId);
        if (node == null || node.getConversation() == null) {
            return;
        }

        ApiRequest apiRequest = node.getConversation().getApiRequest();
        if (apiRequest == null) {
            return;
        }
        if (apiRequest.getAssertion() != null) {
            Assertion assertion = converttoEntity(assertionDTO);
            assertion.setId(apiRequest.getAssertion().getId());
            assertionRepository.save(assertion);
        }
    }

    private Assertion converttoEntity(AssertionDTO assertionDTO) {
        Assertion assertion = new Assertion();
        assertion.setStatusCode(assertionDTO.getStatusCode());
        assertion.setResponseTime(assertionDTO.getResponseTime());
        assertion.setResponseSize(assertionDTO.getResponseSize());
        assertion.setBodyContentType(assertionDTO.getBodyContentType());
        List<BodyAssertDTO> bodyAssertDTOs = assertionDTO.getBodyAssertDTOs();
        List<BodyAssert> list = new ArrayList<BodyAssert>();
        for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
            BodyAssert bodyAssert = new BodyAssert();
            bodyAssert.setPropertyName(bodyAssertDTO.getPropertyName());
            bodyAssert.setComparator(bodyAssertDTO.getComparator());
            bodyAssert.setExpectedValue(bodyAssertDTO.getExpectedValue());
            bodyAssert.setSuccess(bodyAssertDTO.isSuccess());
            bodyAssert.setActualValue(bodyAssertDTO.getActualValue());
            list.add(bodyAssert);
        }
        assertion.setBodyAsserts(list);
        return assertion;
    }
}
