package com.king.yyl.service.utils.verify.request;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.king.yyl.domain.apis.custom.FieldValue;
import com.king.yyl.domain.apis.custom.Proxy;
import com.king.yyl.domain.apis.custom.ScenarioApiInfo;
import com.king.yyl.domain.apis.swagger.HttpMethod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by apple on 16/10/12.
 */

public class SendHttpRequest {

    protected ScenarioApiInfo scenarioApiInfo;
    protected Response response;
    protected String sessionId = "";
    protected String bodyParameters;
    protected Map<String, String> cookieParameters = new HashMap();
    protected Map<String, String> formParameters = new HashMap();
    protected Map<String, String> headerParameters = new HashMap();
    protected Map<String, String> pathParameters = new HashMap();
    protected Map<String, String> queryParameters = new HashMap();

    public SendHttpRequest(ScenarioApiInfo scenarioApiInfo) {

        this.scenarioApiInfo = scenarioApiInfo;

        List<FieldValue> fieldValues = scenarioApiInfo.getFieldValues();

        Map<String, String> headersMap = scenarioApiInfo.getHeaders();

        for (int i = 0; i < fieldValues.size(); i++) {
            if (fieldValues.get(i).getIn().equals("body")) {
                bodyParameters = fieldValues.get(i).getValue();
            } else if (fieldValues.get(i).getIn().equals("cookie")) {
                cookieParameters.put(fieldValues.get(i).getName(), fieldValues.get(i).getValue());
            } else if (fieldValues.get(i).getIn().equals("formData")) {
                formParameters.put(fieldValues.get(i).getName(), fieldValues.get(i).getValue());
            } else if (fieldValues.get(i).getIn().equals("header")) {
                headerParameters.put(fieldValues.get(i).getName(), fieldValues.get(i).getValue());
            } else if (fieldValues.get(i).getIn().equals("path")) {
                pathParameters.put(fieldValues.get(i).getName(), fieldValues.get(i).getValue());
            } else if (fieldValues.get(i).getIn().equals("query")) {
                queryParameters.put(fieldValues.get(i).getName(), fieldValues.get(i).getValue());
            }
        }
        headerParameters.putAll(headersMap);
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getBodyParameters() {
        return bodyParameters;
    }

    public void setBodyParameters(String bodyParameters) {
        this.bodyParameters = bodyParameters;
    }

    public Map<String, String> getCookieParameters() {
        return cookieParameters;
    }

    public void setCookieParameters(Map<String, String> cookieParameters) {
        this.cookieParameters = cookieParameters;
    }

    public Map<String, String> getFormParameters() {
        return formParameters;
    }

    public void setFormParameters(Map<String, String> formParameters) {
        this.formParameters = formParameters;
    }

    public Map<String, String> getHeaderParameters() {
        return headerParameters;
    }

    public void setHeaderParameters(Map<String, String> headerParameters) {
        this.headerParameters = headerParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public ScenarioApiInfo getScenarioApiInfo() {
        return scenarioApiInfo;
    }

    public void setScenarioApiInfo(ScenarioApiInfo scenarioApiInfo) {
        this.scenarioApiInfo = scenarioApiInfo;
    }

    public void sendHttpRequest() {

        String scheme = scenarioApiInfo.getScheme();
        String host = scenarioApiInfo.getHost();
        String basePath = scenarioApiInfo.getBasePath();
        String urlPath = scenarioApiInfo.getPathUrl();
        Proxy proxy = new Proxys().getProxys().get(host);
        HttpMethod httpMethod = scenarioApiInfo.getHttpMethod();

        RestAssured.baseURI = scheme + "://" + host + basePath;

//        if (!this.sessionId.equals("")) {
//            RestAssured.sessionId = this.sessionId;
//        }
        if (proxy != null) {
            RestAssured.proxy(proxy.getHost(), proxy.getPort());
        }
        if (httpMethod.equals(HttpMethod.GET)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().get(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().get(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.POST)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().post(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().post(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.DELETE)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().delete(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().delete(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.HEAD)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().head(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().head(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.OPTIONS)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().options(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().options(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.PUT)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().put(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().put(urlPath, pathParameters);
            }
        } else if (httpMethod.equals(HttpMethod.PATCH)) {
            if (bodyParameters == null) {
                this.response = RestAssured.given().queryParameters(queryParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().patch(urlPath, pathParameters);
            } else {
                this.response = RestAssured.given().queryParameters(queryParameters).body(bodyParameters).cookies(cookieParameters).formParameters(formParameters).contentType("application/json").log().all().headers(headerParameters).then().log().all().patch(urlPath, pathParameters);
            }
        }
    }
}
