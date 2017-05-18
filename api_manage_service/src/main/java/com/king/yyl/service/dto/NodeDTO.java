
package com.king.yyl.service.dto;

import java.util.List;

public class NodeDTO extends BaseDTO {

    private String nodeType;

    private String parentId;

    private Long position;

    private String projectId;

    private Boolean starred;

  //API method type - GET/POST/PUT/DELETE etc.
    private String method;

    private ConversationDTO conversationDTO;

    private GenericEntityDTO genericEntityDTO;

    private List<TagDTO> tags;

    private String apiURL;//scheme + host + port + path

    public String getProjectId() {
	return projectId;
    }

    public void setProjectId(String projectId) {
	this.projectId = projectId;
    }

    public String getParentId() {
	return parentId;
    }

    public void setParentId(String parentId) {
	this.parentId = parentId;
    }

    public ConversationDTO getConversationDTO() {
	return conversationDTO;
    }

    public void setConversationDTO(ConversationDTO conversationDTO) {
	this.conversationDTO = conversationDTO;
    }

    public String getNodeType() {
	return nodeType;
    }

    public void setNodeType(String nodeType) {
	this.nodeType = nodeType;
    }

    public Long getPosition() {
	return position;
    }

    public void setPosition(Long position) {
	this.position = position;
    }

    public Boolean getStarred() {
	return starred;
    }

    public void setStarred(Boolean starred) {
	this.starred = starred;
    }

    public List<TagDTO> getTags() {
	return tags;
    }

    public void setTags(List<TagDTO> tags) {
	this.tags = tags;
    }

    public GenericEntityDTO getGenericEntityDTO() {
	return genericEntityDTO;
    }

    public void setGenericEntityDTO(GenericEntityDTO genericEntityDTO) {
	this.genericEntityDTO = genericEntityDTO;
    }

    public String getMethod() {
	return method;
    }

    public void setMethod(String method) {
	this.method = method;
    }

    public String getApiURL() {
        return apiURL;
    }

    public void setApiURL(String apiURL) {
        this.apiURL = apiURL;
    }

}
