
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

public class BaseNode extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String nodeType;// PROJECT/FOLDER/REQUEST/ENTITY/SOCKET etc.

    private String parentId;

    private String projectId;

    private Long position;// location in the parent node

    private Boolean starred;

    //API method type - GET/POST/PUT/DELETE etc.
    private String method;

    private String workspaceId;

    @DBRef
    private List<Tag> tags;

    @DBRef
    private Conversation conversation;

    @DBRef
    private GenericEntity genericEntity;

    public String getNodeType() {
	return nodeType;
    }

    public void setNodeType(String nodeType) {
	this.nodeType = nodeType;
    }

    public String getParentId() {
	return parentId;
    }

    public void setParentId(String parentId) {
	this.parentId = parentId;
    }

    public Long getPosition() {
	return position;
    }

    public void setPosition(Long position) {
	this.position = position;
    }

    public Conversation getConversation() {
	return conversation;
    }

    public void setConversation(Conversation conversation) {
	this.conversation = conversation;
    }

    public Boolean getStarred() {
	return starred;
    }

    public void setStarred(Boolean starred) {
	this.starred = starred;
    }

    public List<Tag> getTags() {
	return tags;
    }

    public void setTags(List<Tag> tags) {
	this.tags = tags;
    }

    public GenericEntity getGenericEntity() {
	return genericEntity;
    }

    public void setGenericEntity(GenericEntity genericEntity) {
	this.genericEntity = genericEntity;
    }

    public String getProjectId() {
	return projectId;
    }

    public void setProjectId(String projectId) {
	this.projectId = projectId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
}
