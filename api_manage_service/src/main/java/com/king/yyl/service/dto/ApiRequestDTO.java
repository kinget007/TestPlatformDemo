
package com.king.yyl.service.dto;

import com.king.yyl.domain.auth.Auth;
import com.king.yyl.domain.parameters.Parameter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class ApiRequestDTO extends BaseDTO {

    private static final long serialVersionUID = 1L;

    private String apiUrl;

    private String methodType;

    private List<Parameter> parameters = new ArrayList<>();

    private Auth auth;

    private String conversationId;

    @DBRef
    private AssertionDTO assertionDTO;

    private String workspaceId;

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }


    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public AssertionDTO getAssertionDTO() {
        return assertionDTO;
    }

    public void setAssertionDTO(AssertionDTO assertionDTO) {
        this.assertionDTO = assertionDTO;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
