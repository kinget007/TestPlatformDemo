
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Project extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @DBRef
    private BaseNode projectRef;

    public BaseNode getProjectRef() {
	return projectRef;
    }

    public void setProjectRef(BaseNode projectRef) {
	this.projectRef = projectRef;
    }
}
