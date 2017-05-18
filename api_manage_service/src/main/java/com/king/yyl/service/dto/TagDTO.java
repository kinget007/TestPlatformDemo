
package com.king.yyl.service.dto;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class TagDTO extends BaseDTO {

    private ColorDTO color;

    @DBRef
    private WorkspaceDTO workspace;

    public ColorDTO getColor() {
	return color;
    }

    public void setColor(ColorDTO color) {
	this.color = color;
    }

    public WorkspaceDTO getWorkspace() {
	return workspace;
    }

    public void setWorkspace(WorkspaceDTO workspace) {
	this.workspace = workspace;
    }
}
