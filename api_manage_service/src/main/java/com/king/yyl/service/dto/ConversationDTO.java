package com.king.yyl.service.dto;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Date;

public class ConversationDTO extends BaseDTO {

    @JsonManagedReference
    private ApiRequestDTO ApiRequestDTO;

    @JsonManagedReference
    private ApiResponseDTO ApiResponseDTO;

    private Long duration;

    @JsonBackReference
    private NodeDTO nodeDTO;

    private String workspaceId;

    private Date lastRunDate;

    public ApiRequestDTO getApiRequestDTO() {
	return ApiRequestDTO;
    }

    public void setApiRequestDTO(ApiRequestDTO ApiRequestDTO) {
	this.ApiRequestDTO = ApiRequestDTO;
    }

    public ApiResponseDTO getApiResponseDTO() {
	return ApiResponseDTO;
    }

    public void setApiResponseDTO(ApiResponseDTO ApiResponseDTO) {
	this.ApiResponseDTO = ApiResponseDTO;
    }

    public Long getDuration() {
	return duration;
    }

    public void setDuration(Long duration) {
	this.duration = duration;
    }


    public NodeDTO getNodeDTO() {
        return nodeDTO;
    }

    public void setNodeDTO(NodeDTO nodeDTO) {
        this.nodeDTO = nodeDTO;
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
