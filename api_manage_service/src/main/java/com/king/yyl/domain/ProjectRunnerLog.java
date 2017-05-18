
package com.king.yyl.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class ProjectRunnerLog extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    @OneToMany
    private List<ProjectRunnerLogItem> logItems = new ArrayList<ProjectRunnerLogItem>();

    @ManyToOne
    @JsonBackReference
    private Project project;

    public Project getProject() {
	return project;
    }

    public void setProject(Project project) {
	this.project = project;
    }

    public List<ProjectRunnerLogItem> getLogItems() {
	return logItems;
    }

    public void setLogItems(List<ProjectRunnerLogItem> logItems) {
	this.logItems = logItems;
    }

}
