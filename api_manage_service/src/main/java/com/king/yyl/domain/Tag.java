
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class Tag extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private Color color;

    @DBRef
    private Workspace workspace;

    public Color getColor() {
	return color;
    }

    public void setColor(Color color) {
	this.color = color;
    }

    public Workspace getWorkspace() {
	return workspace;
    }

    public void setWorkspace(Workspace workspace) {
	this.workspace = workspace;
    }
}
