package com.king.yyl.web.rest;

import com.king.yyl.domain.BaseNode;
import com.king.yyl.domain.Project;
import com.king.yyl.domain.Workspace;
import com.king.yyl.repository.NodeRepository;
import com.king.yyl.repository.ProjectRepository;
import com.king.yyl.repository.WorkspaceRepository;
import com.king.yyl.service.dto.NodeDTO;
import com.king.yyl.service.dto.ProjectDTO;
import com.king.yyl.service.enums.NodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProjectResource {
    Logger logger = LoggerFactory.getLogger(ProjectResource.class);

    @Inject
    private WorkspaceRepository workspaceRepository;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private NodeRepository nodeRepository;

    @RequestMapping(value="/workspaces/{workspaceId}/projects", method = RequestMethod.POST, headers = "Accept=application/json")
    public Project create(@PathVariable("workspaceId") String workspaceId, @RequestBody ProjectDTO projectDTO) {
	logger.debug("Creating a new project with information: " + projectDTO);

	// Create project
	Project project = new Project();
	project.setName(projectDTO.getName());
	project.setDescription(projectDTO.getDescription());

	// Create project reference node
	BaseNode projectRef = new BaseNode();
	projectRef.setName(projectDTO.getName());
	projectRef.setNodeType(NodeType.PROJECT.name());
	projectRef.setParentId("-1");
	projectRef.setPosition(Long.valueOf(0));
	projectRef.setWorkspaceId(workspaceId);

	// Save project reference node
	BaseNode savedRef = nodeRepository.save(projectRef);

	// Set project reference node
	project.setProjectRef(savedRef);

	Project savedProject = projectRepository.save(project);

	// Update projectRef (Set projectId to the reference node)
	projectRef.setProjectId(savedProject.getId());
	savedRef = nodeRepository.save(projectRef);

	// Update workspace
	Workspace workspace = workspaceRepository.findOne(workspaceId);
	workspace.getProjects().add(savedProject);
	workspaceRepository.save(workspace);

	return savedProject;
    }

    @RequestMapping(value="/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void delete(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id) {
	logger.debug("Deleting project with id: " + id);

	Project projectToBeDeleted = projectRepository.findOne(id);

	List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);

	nodeRepository.delete(listOfNodes);

	projectRepository.delete(projectToBeDeleted);

	Workspace workspace = workspaceRepository.findOne(workspaceId);
	workspace.getProjects().remove(projectToBeDeleted);
	workspaceRepository.save(workspace);
    }

    @RequestMapping(value="/workspaces/{workspaceId}/projects", method = RequestMethod.GET)
    public List<Project> findProjectsFromAWorkspace(@PathVariable("workspaceId") String workspaceId) {
	logger.debug("Finding all projects from workspace with id " + workspaceId);
        List<Project> list = new ArrayList<>();
        for(Project project: projectRepository.findAll()){
            if(project.getProjectRef().getWorkspaceId().equals(workspaceId)){
                list.add(project);
            }
        }
//	return projectRepository.findProjectsFromAWorkspace(workspaceId);
        return list;
    }

    public List<Project> findAll() {
	logger.debug("Finding all projects");

	return projectRepository.findAll();
    }

    @RequestMapping(value="/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.GET)
    public Project findById(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id) {
	logger.debug("Finding project by id: " + id);

	return projectRepository.findOne(id);
    }

    @RequestMapping(value="/workspaces/{workspaceId}/projects/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public @ResponseBody
    Project update(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id, @RequestBody ProjectDTO updated) {
	logger.debug("Updating project with information: " + updated);

	Project project = projectRepository.findOne(updated.getId());
	BaseNode projectRef = project.getProjectRef();

	String updatedName = updated.getName();
	if (updatedName != null) {
	    project.setName(updatedName);
	    projectRef.setName(updatedName);
	}

	String updatedDescription = updated.getDescription();
	if (updatedDescription != null) {
	    project.setDescription(updatedDescription);
	    projectRef.setDescription(updatedDescription);
	}

	nodeRepository.save(projectRef);
	projectRepository.save(project);

	return project;
    }

    @RequestMapping(value="/workspaces/{workspaceId}/projects/{id}/copy", method = RequestMethod.POST, headers = "Accept=application/json")
    public void copy(@PathVariable("workspaceId") String workspaceId, @PathVariable("id") String id, @RequestBody NodeDTO nodeDTO) {
	String nodeType = nodeDTO.getNodeType();
	if (!NodeType.PROJECT.name().equalsIgnoreCase(nodeType)) {
	    return;
	}
	String projectId = nodeDTO.getProjectId();
	Project project = projectRepository.findOne(projectId);
	BaseNode projectRef = project.getProjectRef();

	BaseNode node = nodeRepository.findOne(id);
	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());
	// TODO : Use NodeController#copyNodesRecursively.
	// copyNodesRecursively(node, node.getParentId());
    }

}
