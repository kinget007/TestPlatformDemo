
package com.king.yyl.service.handler.http.builder;

import com.king.yyl.service.dto.ApiRequestDTO;
//import com.king.yyl.service.handler.http.auth.DigestAuthHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ApiHttpClientBuilder {

//    @Autowired
//    private DigestAuthHandler digestAuthHandler;

    public CloseableHttpClient build(ApiRequestDTO apiRequestDTO, HttpUriRequest request) {
	HttpClientBuilder clientBuilder = HttpClientBuilder.create();

	//Set Digest Authentication
//	digestAuthHandler.setCredentialsProvider(apiRequestDTO, clientBuilder);

	CloseableHttpClient httpClient = clientBuilder.build();
	return httpClient;
    }
}
