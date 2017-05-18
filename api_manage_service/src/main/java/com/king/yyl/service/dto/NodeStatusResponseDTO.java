
package com.king.yyl.service.dto;

public class NodeStatusResponseDTO extends BaseDTO {
    private String apiUrl;
    private String methodType;
    private String statusCode;
    private Long duration;
    private Integer successAsserts;
    private Integer failureAsserts;

    public String getApiUrl() {
	return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
	this.apiUrl = apiUrl;
    }

    public String getMethodType() {
	return methodType;
    }

    public void setMethodType(String methodType) {
	this.methodType = methodType;
    }

    public String getStatusCode() {
	return statusCode;
    }

    public void setStatusCode(String statusCode) {
	this.statusCode = statusCode;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Integer getSuccessAsserts() {
        return successAsserts;
    }

    public void setSuccessAsserts(Integer successAsserts) {
        this.successAsserts = successAsserts;
    }

    public Integer getFailureAsserts() {
        return failureAsserts;
    }

    public void setFailureAsserts(Integer failureAsserts) {
        this.failureAsserts = failureAsserts;
    }

}
