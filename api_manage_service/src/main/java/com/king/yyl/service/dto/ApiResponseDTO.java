package com.king.yyl.service.dto;

import com.king.yyl.domain.parameters.HeaderParameter;

import java.util.List;

public class ApiResponseDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    private String responseBody;

    private List<HeaderParameter> headerParameters;

    private String workspaceId;

    private String conversationId;

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public List<HeaderParameter> getHeaderParameters() {
        return headerParameters;
    }

    public void setHeaderParameters(List<HeaderParameter> headerParameters) {
        this.headerParameters = headerParameters;
    }

    public String getBody() {
        return responseBody;
    }

    public void setBody(String body) {
        this.responseBody = body;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
