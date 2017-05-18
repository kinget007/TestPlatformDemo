
package com.king.yyl.service.dto;

import java.util.ArrayList;
import java.util.List;

public class WorkspaceDTO extends BaseDTO {

    private List<ProjectDTO> projects = new ArrayList<ProjectDTO>();

    public List<ProjectDTO> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectDTO> projects) {
        this.projects = projects;
    }
}
