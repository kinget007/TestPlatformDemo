
package com.king.yyl.web.rest;

import com.king.yyl.domain.ApiRequest;
import com.king.yyl.repository.ApiRequestRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ApiRequestResource {

    @Inject
    private ApiRequestRepository apiRequestRepository;

//    @Inject
//    private HttpRequestHeaderRepository requestHeaderRepository;

    @RequestMapping(value="/requests/api-urls", method = RequestMethod.GET)
    public List<String> findUniqueApiUrls() {
        List<ApiRequest> apiRequests = apiRequestRepository.findAll();
        Set<String> uniqueRfRequests = new HashSet<>();
        for (ApiRequest apiRequest : apiRequests) {
            uniqueRfRequests.add(apiRequest.getApiUrl());
        }

        List<String> list = new ArrayList<>();
        list.addAll(uniqueRfRequests);
        return list;
    }

//    @RequestMapping(value="/requests/http-headers", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    List<String> findHttpRequestHeaders() {
//        List<HeaderParameter> requestHeaders = requestHeaderRepository.findAll();
//
//        List<String> headers = new ArrayList<String>();
//        for (HeaderParameter headerParameter : requestHeaders) {
//            headers.add(headerParameter.getName());
//        }
//        return headers;
//    }

}
