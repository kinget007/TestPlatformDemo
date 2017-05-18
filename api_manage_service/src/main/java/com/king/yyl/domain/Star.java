
package com.king.yyl.domain;

import javax.persistence.ManyToOne;

public class Star extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String userId;

    private String userName;

    @ManyToOne
    private Workspace workspace;

    @ManyToOne
    private Project project;

    @ManyToOne
    private BaseNode baseNode;

    public Workspace getWorkspace() {
	return workspace;
    }

    public void setWorkspace(Workspace workspace) {
	this.workspace = workspace;
    }

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public BaseNode getBaseNode() {
	return baseNode;
    }

    public void setBaseNode(BaseNode baseNode) {
	this.baseNode = baseNode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
