
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

public class Conversation extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @DBRef
    private ApiRequest apiRequest;

    @DBRef
    private ApiResponse apiResponse;

    private Long duration;

    private String nodeId;

    private String workspaceId;

    private Date lastRunDate;

    public ApiRequest getApiRequest() {
        return apiRequest;
    }

    public void setApiRequest(ApiRequest apiRequest) {
        this.apiRequest = apiRequest;
    }

    public ApiResponse getApiResponse() {
        return apiResponse;
    }

    public void setApiResponse(ApiResponse apiResponse) {
        this.apiResponse = apiResponse;
    }

    public Long getDuration() {
	return duration;
    }

    public void setDuration(Long duration) {
	this.duration = duration;
    }

    public String getNodeId() {
	return nodeId;
    }

    public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
    }

    public String getWorkspaceId() {
	return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
	this.workspaceId = workspaceId;
    }

    public Date getLastRunDate() {
        return lastRunDate;
    }

    public void setLastRunDate(Date lastRunDate) {
        this.lastRunDate = lastRunDate;
    }
}
