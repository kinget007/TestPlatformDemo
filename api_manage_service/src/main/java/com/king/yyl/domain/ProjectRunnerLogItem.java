
package com.king.yyl.domain;

public class ProjectRunnerLogItem extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private Long nodeId;

    private Long loggedConversationId;

    public Long getNodeId() {
	return nodeId;
    }

    public void setNodeId(Long nodeId) {
	this.nodeId = nodeId;
    }

    public Long getLoggedConversationId() {
	return loggedConversationId;
    }

    public void setLoggedConversationId(Long loggedConversationId) {
	this.loggedConversationId = loggedConversationId;
    }

}
