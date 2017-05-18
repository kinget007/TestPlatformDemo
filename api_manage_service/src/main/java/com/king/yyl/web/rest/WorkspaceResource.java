package com.king.yyl.web.rest;

import com.king.yyl.domain.Project;
import com.king.yyl.domain.Workspace;
import com.king.yyl.repository.WorkspaceRepository;
import com.king.yyl.service.dto.WorkspaceDTO;
import com.king.yyl.web.rest.util.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkspaceResource {
    Logger logger = LoggerFactory.getLogger(WorkspaceResource.class);

    @Resource
    private ProjectResource projectController;

    @Resource
    private NodeResource nodeController;

    @Inject
    private WorkspaceRepository workspaceRepository;

    @RequestMapping(value="/workspaces", method = RequestMethod.POST, headers = "Accept=application/json")
    public Workspace create(@RequestBody WorkspaceDTO workspaceDTO) {
        logger.debug("Creating a new workspace with information: " + workspaceDTO);

        Workspace workspace = new Workspace();

        workspace.setName(workspaceDTO.getName());
        workspace.setDescription(workspaceDTO.getDescription());

        return workspaceRepository.save(workspace);
    }

    @RequestMapping(value="/workspaces/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void delete(@PathVariable("id") String id) {
        logger.debug("Deleting workspace with id: " + id);

        Workspace deleted = workspaceRepository.findOne(id);

        List<Project> projects = deleted.getProjects();

        for (Project project : projects) {
            projectController.delete(id, project.getId());
        }

        workspaceRepository.delete(deleted);
    }

    @RequestMapping(value="/workspaces", method = RequestMethod.GET)
    public List<Workspace> findAll(@RequestParam(value = "name", required = false) String name) {
        logger.debug("Finding all workspaces");
        if (name != null && !name.isEmpty()) {
            List<Workspace> workspaces = workspaceRepository.findByName(name);
            return workspaces;
        }
        return workspaceRepository.findAll();
    }

    @RequestMapping(value="/workspaces/{id}", method = RequestMethod.GET)
    public Workspace findById(@PathVariable("id") String id) {
        logger.debug("Finding workspace by id: " + id);

        return workspaceRepository.findOne(id);
    }

    @RequestMapping(value="/workspaces/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public Workspace update(@PathVariable("id") String id, @RequestBody WorkspaceDTO updated) {
        logger.debug("Updating workspace with information: " + updated);

        Workspace workspace = workspaceRepository.findOne(updated.getId());

        workspace.setName(updated.getName());
        workspace.setDescription(updated.getDescription());

        workspaceRepository.save(workspace);

        return workspace;
    }

    @RequestMapping(value="/workspaces/{id}/export", method = RequestMethod.GET)
    public List<TreeNode> export(@PathVariable("id") String id) {
        Workspace workspace = workspaceRepository.findOne(id);
        List<Project> projects = workspace.getProjects();

        List<TreeNode> projectTreeList = new ArrayList<TreeNode>();
        TreeNode projectTree = null;
        for (Project project : projects) {
            projectTree = nodeController.getProjectTree(project.getProjectRef().getId());
            projectTreeList.add(projectTree);
        }

        return projectTreeList;
    }

}
