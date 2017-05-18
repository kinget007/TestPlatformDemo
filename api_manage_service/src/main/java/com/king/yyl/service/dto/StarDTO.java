
package com.king.yyl.service.dto;

/**
 * User reference is not required as Star items are mapped against logged-in user.
 */
public class StarDTO extends BaseDTO {

    private Long workspaceId;

    public Long getWorkspaceId() {
	return workspaceId;
    }

    public void setWorkspaceId(Long workspaceId) {
	this.workspaceId = workspaceId;
    }
}
