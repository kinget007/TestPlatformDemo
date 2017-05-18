
package com.king.yyl.service.handler.http;

import com.king.yyl.domain.parameters.HeaderParameter;
import com.king.yyl.service.dto.ApiRequestDTO;
import com.king.yyl.service.dto.ApiResponseDTO;
import com.king.yyl.service.dto.AssertionDTO;
import com.king.yyl.service.handler.http.builder.ApiRequestBuilder;
import com.king.yyl.service.handler.http.builder.ApiHttpClientBuilder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GenericHandler {
    Logger logger = LoggerFactory.getLogger(GenericHandler.class);

    @Autowired
    ApiRequestBuilder apiRequestBuilder;

    @Autowired
    ApiHttpClientBuilder apiHttpClientBuilder;

    /**
     * This method will be used for API processing and the method below this will be deprecated.
     */
    public ApiResponseDTO processHttpRequest(ApiRequestDTO apiRequestDTO) {
        HttpUriRequest httpUriRequest = apiRequestBuilder.build(apiRequestDTO);

        CloseableHttpClient httpClient = apiHttpClientBuilder.build(apiRequestDTO, httpUriRequest);

        ApiResponseDTO responseDTO = null;
        try {
            long startTime = System.currentTimeMillis();
            CloseableHttpResponse httpResponse = httpClient.execute(httpUriRequest);
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            responseDTO = buildApiResponse(httpResponse, duration, apiRequestDTO);
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return responseDTO;
    }

    private ApiResponseDTO buildApiResponse(CloseableHttpResponse httpResponse, long duration, ApiRequestDTO apiRequestDTO) throws IOException {
        ApiResponseDTO responseDTO = buildApiResponse(httpResponse);

        AssertionDTO assertionDTO = apiRequestDTO.getAssertionDTO() != null ? apiRequestDTO.getAssertionDTO() : new AssertionDTO();

        assertionDTO.setResponseTime((int) duration);
        HttpEntity entity = httpResponse.getEntity();
        assertionDTO.setBodyContentType(entity.getContentType() != null ? entity.getContentType().getValue() : null);
        assertionDTO.setResponseSize(responseDTO.getBody().length());
        assertionDTO.setStatusCode(httpResponse.getStatusLine().getStatusCode());
        return responseDTO;
    }

    public ApiResponseDTO processHttpRequest(HttpRequestBase baseRequest, CloseableHttpClient httpclient) throws IOException {
        Header[] requestHeaders = baseRequest.getAllHeaders();
        logger.info("request headers length : " + requestHeaders.length);

        for (Header requestHeader : requestHeaders) {
            logger.info("request header - name : " + requestHeader.getName() + " value : " + requestHeader.getValue());
        }

        CloseableHttpResponse httpResponse = httpclient.execute(baseRequest);

        ApiResponseDTO responseDTO = buildApiResponse(httpResponse);
        return responseDTO;
    }

    private ApiResponseDTO buildApiResponse(CloseableHttpResponse httpResponse) throws IOException {
        ApiResponseDTO responseDTO = new ApiResponseDTO();
        String responseBody = "";
        List<HeaderParameter> headers = new ArrayList<>();
        try {
            logger.info("response status : " + httpResponse.getStatusLine());
            responseDTO.setStatus(httpResponse.getStatusLine().getStatusCode() + " " + httpResponse.getStatusLine().getReasonPhrase());
            HttpEntity responseEntity = httpResponse.getEntity();
            Header[] responseHeaders = httpResponse.getAllHeaders();

            HeaderParameter headerParameter = null;
            for (Header responseHeader : responseHeaders) {
                headerParameter = new HeaderParameter();
                headerParameter.setName(responseHeader.getName());
                headerParameter.setValue(responseHeader.getValue());
                headers.add(headerParameter);
            }
            Header contentType = responseEntity.getContentType();
            logger.info("response contentType : " + contentType);

            responseBody = EntityUtils.toString(responseEntity);
            EntityUtils.consume(responseEntity);
        } finally {
            httpResponse.close();
        }
        responseDTO.setBody(responseBody);
        responseDTO.setHeaderParameters(headers);
        return responseDTO;
    }

    public ApiResponseDTO process(ApiRequestDTO ApiRequestDTO) throws IOException {
        return null;
    }

}
