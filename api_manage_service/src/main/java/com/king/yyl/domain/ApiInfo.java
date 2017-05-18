package com.king.yyl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ApiInfo.
 */

@Document(collection = "api_info")
public class ApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("swagger_uri")
    private String swaggerUri;

    @Field("version")
    private String version;

    @Field("scheme")
    private String scheme;

    @Field("host")
    private String host;

    @Field("port")
    private Integer port;

    @Field("path")
    private String path;

    @Field("operation")
    private String operation;

    @Field("cookie_params")
    private String cookieParams;

    @Field("form_params")
    private String formParams;

    @Field("query_params")
    private String queryParams;

    @Field("path_params")
    private String pathParams;

    @Field("head_params")
    private String headParams;

    @Field("body_params")
    private String bodyParams;

    @Field("request_desc")
    private String requestDesc;

    @Field("response_desc")
    private String responseDesc;

    @Field("checks")
    private String checks;

    @Field("proxys")
    private String proxys;

    @Field("project_id")
    private String projectId;

    @Field("project_description")
    private String projectDescription;

    @Field("etc")
    private String etc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ApiInfo description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwaggerUri() {
        return swaggerUri;
    }

    public ApiInfo swaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
        return this;
    }

    public void setSwaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
    }

    public String getVersion() {
        return version;
    }

    public ApiInfo version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScheme() {
        return scheme;
    }

    public ApiInfo scheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getHost() {
        return host;
    }

    public ApiInfo host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public ApiInfo port(Integer port) {
        this.port = port;
        return this;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public ApiInfo path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOperation() {
        return operation;
    }

    public ApiInfo operation(String operation) {
        this.operation = operation;
        return this;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCookieParams() {
        return cookieParams;
    }

    public ApiInfo cookieParams(String cookieParams) {
        this.cookieParams = cookieParams;
        return this;
    }

    public void setCookieParams(String cookieParams) {
        this.cookieParams = cookieParams;
    }

    public String getFormParams() {
        return formParams;
    }

    public ApiInfo formParams(String formParams) {
        this.formParams = formParams;
        return this;
    }

    public void setFormParams(String formParams) {
        this.formParams = formParams;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public ApiInfo queryParams(String queryParams) {
        this.queryParams = queryParams;
        return this;
    }

    public void setQueryParams(String queryParams) {
        this.queryParams = queryParams;
    }

    public String getPathParams() {
        return pathParams;
    }

    public ApiInfo pathParams(String pathParams) {
        this.pathParams = pathParams;
        return this;
    }

    public void setPathParams(String pathParams) {
        this.pathParams = pathParams;
    }

    public String getHeadParams() {
        return headParams;
    }

    public ApiInfo headParams(String headParams) {
        this.headParams = headParams;
        return this;
    }

    public void setHeadParams(String headParams) {
        this.headParams = headParams;
    }

    public String getBodyParams() {
        return bodyParams;
    }

    public ApiInfo bodyParams(String bodyParams) {
        this.bodyParams = bodyParams;
        return this;
    }

    public void setBodyParams(String bodyParams) {
        this.bodyParams = bodyParams;
    }

    public String getRequestDesc() {
        return requestDesc;
    }

    public ApiInfo requestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
        return this;
    }

    public void setRequestDesc(String requestDesc) {
        this.requestDesc = requestDesc;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public ApiInfo responseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
        return this;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public String getChecks() {
        return checks;
    }

    public ApiInfo checks(String checks) {
        this.checks = checks;
        return this;
    }

    public void setChecks(String checks) {
        this.checks = checks;
    }

    public String getProxys() {
        return proxys;
    }

    public ApiInfo proxys(String proxys) {
        this.proxys = proxys;
        return this;
    }

    public void setProxys(String proxys) {
        this.proxys = proxys;
    }

    public String getProjectId() {
        return projectId;
    }

    public ApiInfo projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public ApiInfo projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getEtc() {
        return etc;
    }

    public ApiInfo etc(String etc) {
        this.etc = etc;
        return this;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ApiInfo apiInfo = (ApiInfo) o;
        if (apiInfo.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, apiInfo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApiInfo{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", swaggerUri='" + swaggerUri + "'" +
            ", version='" + version + "'" +
            ", scheme='" + scheme + "'" +
            ", host='" + host + "'" +
            ", port='" + port + "'" +
            ", path='" + path + "'" +
            ", operation='" + operation + "'" +
            ", cookieParams='" + cookieParams + "'" +
            ", formParams='" + formParams + "'" +
            ", queryParams='" + queryParams + "'" +
            ", pathParams='" + pathParams + "'" +
            ", headParams='" + headParams + "'" +
            ", bodyParams='" + bodyParams + "'" +
            ", requestDesc='" + requestDesc + "'" +
            ", responseDesc='" + responseDesc + "'" +
            ", checks='" + checks + "'" +
            ", proxys='" + proxys + "'" +
            ", projectId='" + projectId + "'" +
            ", projectDescription='" + projectDescription + "'" +
            ", etc='" + etc + "'" +
            '}';
    }
}
