
package com.king.yyl.service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class ProjectDTO extends BaseDTO {

    @JsonBackReference
    private NodeDTO projectRef;

    public NodeDTO getProjectRef() {
	return projectRef;
    }

    public void setProjectRef(NodeDTO projectRef) {
	this.projectRef = projectRef;
    }
}
