
package com.king.yyl.domain;

import com.king.yyl.domain.parameters.HeaderParameter;

import java.util.List;

public class ApiResponse extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String responseBody;

    private List<HeaderParameter> headers;

    private String conversationId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public List<HeaderParameter> getHeaders() {
        return headers;
    }

    public void setHeaders(List<HeaderParameter> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return responseBody;
    }

    public void setBody(String responseBody) {
        this.responseBody = responseBody;
    }

}
