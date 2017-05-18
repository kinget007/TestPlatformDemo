
package com.king.yyl.service.handler.http.builder;

import com.king.yyl.domain.parameters.*;
import com.king.yyl.service.dto.ApiRequestDTO;
import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//import com.king.yyl.service.handler.http.auth.BasicAuthHandler;

@Component
public class ApiRequestBuilder {
    Logger logger = LoggerFactory.getLogger(ApiRequestBuilder.class);

//    @Autowired
//    private BasicAuthHandler basicAuthHandler;

    public HttpUriRequest build(ApiRequestDTO apiRequestDTO) {
        String methodType = apiRequestDTO.getMethodType();

        RequestBuilder requestBuilder = RequestBuilder.create(methodType);

        setUriWithParams(apiRequestDTO, requestBuilder);

        setHeaders(apiRequestDTO, requestBuilder);

//        basicAuthHandler.setBasicAuthWithBase64Encode(apiRequestDTO, requestBuilder);

        setRequestEntity(apiRequestDTO, requestBuilder);

        HttpUriRequest httpUriRequest = requestBuilder.build();
        return httpUriRequest;
    }

    private void setUriWithParams(ApiRequestDTO apiRequestDTO, RequestBuilder requestBuilder) {
        String apiUrl = apiRequestDTO.getApiUrl();
        List<Parameter> params = apiRequestDTO.getParameters();
        List<QueryParameter> queryParameters = new ArrayList<>();
        List<PathParameter> pathParameters = new ArrayList<>();

        for (int i = 0; i < params.size(); i++) {
            if (params.get(i).getIn().equals("query")) {
                queryParameters.add((QueryParameter) params.get(i));
            } else if (params.get(i).getIn().equals("path")) {
                pathParameters.add((PathParameter) params.get(i));
            }
        }

        if (queryParameters != null && !queryParameters.isEmpty()) {
            apiUrl = buildWithQueryParams(apiUrl, queryParameters);
        }

        if (pathParameters != null && !pathParameters.isEmpty()) {
            apiUrl = buildWithPathParams(apiUrl, pathParameters);
        }

        requestBuilder.setUri(apiUrl);
    }

    private String buildWithQueryParams(String apiUrl, List<QueryParameter> queryParameters) {
        StringBuilder sb = new StringBuilder();
        sb.append(apiUrl);
        boolean paramsAlreadyExist = false;
        if (apiUrl.indexOf("?") > 0) {
            paramsAlreadyExist = true;
        }
        for (int i = 0; i < queryParameters.size(); i++) {

            if (i == 0 && !paramsAlreadyExist) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            try {
                sb.append(URLEncoder.encode(queryParameters.get(i).getName(), "UTF-8") + "=" + URLEncoder.encode(queryParameters.get(i).getValue(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.toString());
            }

        }
        return sb.toString();
    }

    private String buildWithPathParams(String apiUrl, List<PathParameter> pathParameters) {
        String pathUrl = apiUrl;
        logger.info("apiUrl : " + apiUrl);
        if(pathParameters.size()>0){
            for (int i = 0; i < pathParameters.size(); i++) {
                pathUrl = pathUrl.replace("{"+pathParameters.get(i).getName()+"}",pathParameters.get(i).getValue());
            }
        }
        return pathUrl;
    }

    private void setHeaders(ApiRequestDTO apiRequestDTO, RequestBuilder requestBuilder) {
        List<Parameter> parameters = apiRequestDTO.getParameters();
        List<HeaderParameter> headers = new ArrayList<>();

        if(parameters.size()>0){
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).getIn().contentEquals("header")) {
                    headers.add((HeaderParameter) parameters.get(i));
                }
            }

            if (headers != null && !headers.isEmpty() && headers.size()>0) {
                boolean contentTypeFound = false;
                for (HeaderParameter headerParameter : headers) {
                    if (headerParameter.getName() != null && headerParameter.getValue() != null) {
                        requestBuilder.addHeader(headerParameter.getName(), headerParameter.getValue());
                    }
                    if (HttpHeaders.CONTENT_TYPE.equalsIgnoreCase(headerParameter.getName())) {
                        contentTypeFound = true;
                    }
                }
                if (!contentTypeFound) {
                    requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                }
            } else {
                requestBuilder.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            }
        }
    }

    private void setRequestEntity(ApiRequestDTO apiRequestDTO, RequestBuilder requestBuilder) {
        List<Parameter> parameters = apiRequestDTO.getParameters();
        List<FormParameter> formParams = new ArrayList<>();
        List<BodyParameter> bodyParameter = new ArrayList<>();


        if(parameters.size()>0){
            for (int i = 0; i < parameters.size(); i++) {
                if (parameters.get(i).getIn().contentEquals("form")) {
                    formParams.add((FormParameter) parameters.get(i));
                } else if (parameters.get(i).getIn().contentEquals("body")) {
                    if((parameters.get(i)).getValue().trim().length() >0){
                        bodyParameter.add((BodyParameter) parameters.get(i));
                    }
                }
            }
        }

        if (bodyParameter.size()>0) {
            try {
                requestBuilder.setEntity(new StringEntity(bodyParameter.get(0).getValue()));

            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        } else if (formParams != null && !formParams.isEmpty() && formParams.size() > 0) {
            requestBuilder.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
            List<NameValuePair> formEntity = new ArrayList<NameValuePair>();
            for (FormParameter formParameter : formParams) {
                formEntity.add(new BasicNameValuePair(formParameter.getName(), formParameter.getValue()));
            }
            try {
                requestBuilder.setEntity(new UrlEncodedFormEntity(formEntity));
            } catch (UnsupportedEncodingException e) {
            }
        }
    }
}
