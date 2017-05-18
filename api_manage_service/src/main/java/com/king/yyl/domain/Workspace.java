
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class Workspace extends NamedEntity {
    private static final long serialVersionUID = 1L;

    @DBRef
    private List<Project> projects = new ArrayList<Project>();

    @DBRef
    private List<Tag> tags = new ArrayList<Tag>();

    public List<Project> getProjects() {
	return projects;
    }

    public void setProjects(List<Project> projects) {
	this.projects = projects;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
