package com.king.yyl.web.rest;

import com.king.yyl.domain.*;
import com.king.yyl.repository.*;
import com.king.yyl.repository.util.TreeNodeBuilder;
import com.king.yyl.service.dto.NodeDTO;
import com.king.yyl.service.dto.TagDTO;
import com.king.yyl.service.enums.NodeType;
import com.king.yyl.web.rest.util.EntityToDTO;
import com.king.yyl.web.rest.util.NodeUtil;
import com.king.yyl.web.rest.util.TreeNode;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.MalformedURLException;
import java.util.*;

@RestController
@RequestMapping("/api")
public class NodeResource {
    Logger logger = LoggerFactory.getLogger(NodeResource.class);

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private NodeRepository nodeRepository;

    @Inject
    private TagRepository tagRepository;

    @Inject
    private ConversationRepository conversationRepository;

    @Inject
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenerateApiResource generateApiController;

    // Note : Creating a node requires parentId. Project-node is the root node and it is created during project creation.
    @RequestMapping(value="/nodes/{parentId}/children", method = RequestMethod.POST, headers = "Accept=application/json")
    public NodeDTO create(@PathVariable("parentId") String parentId, @RequestBody NodeDTO nodeDTO) throws JSONException, MalformedURLException {
	logger.debug("Creating a new node with information: " + nodeDTO);

	BaseNode node = new BaseNode();

	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());
	node.setNodeType(nodeDTO.getNodeType());
	node.setStarred(nodeDTO.getStarred());
	BaseNode parentNode = nodeRepository.findOne(parentId);
	node.setWorkspaceId(parentNode.getWorkspaceId());

	node.setParentId(parentId);

	// To find the last child node and its position
	long lastChildPosition = NodeUtil.findLastChildPosition(nodeRepository.getChildren(parentId));

	// Set the appropriate node position.
	node.setPosition(lastChildPosition + 1);

	node = nodeRepository.save(node);

	if (nodeDTO.getConversationDTO() != null && nodeDTO.getConversationDTO().getId() != null) {
	    Conversation conversation = conversationRepository.findOne(nodeDTO.getConversationDTO().getId());
	    node.setConversation(conversation);
	    conversation.setNodeId(node.getId());
	    conversationRepository.save(conversation);
	}

	if (nodeDTO.getGenericEntityDTO() != null && nodeDTO.getGenericEntityDTO().getId() != null) {
	    GenericEntity genericEntity = genericEntityRepository.findOne(nodeDTO.getGenericEntityDTO().getId());
	    genericEntity.setBaseNodeId(node.getId());
	    genericEntityRepository.save(genericEntity);
	    node.setGenericEntity(genericEntity);
	}

	Project project = projectRepository.findOne(nodeDTO.getProjectId());
	node.setProjectId(project.getId());

	BaseNode savedNode = nodeRepository.save(node);

	// set tags
	List<Tag> tags = new ArrayList<Tag>();

	List<TagDTO> tagDTOs = nodeDTO.getTags();
	if (tagDTOs != null && !tagDTOs.isEmpty()) {
	    List<String> tagIds = new ArrayList<String>();
	    for (TagDTO tagDTO : tagDTOs) {
		tagIds.add(tagDTO.getId());
	    }
	    tags = (List<Tag>) tagRepository.findAll(tagIds);
	}
	savedNode.setTags(tags);
	savedNode = nodeRepository.save(savedNode);

	// Generate APIs for Entity
	if (nodeDTO.getGenericEntityDTO() != null && nodeDTO.getGenericEntityDTO().getId() != null) {
	    generateApiController.generateApi(savedNode);
	}
	return EntityToDTO.toDTO(savedNode);
    }

    @RequestMapping(value="/nodes/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public void delete(@PathVariable("id") String id) {
	logger.debug("Deleting node with id: " + id);

	BaseNode nodeToDelete = nodeRepository.findOne(id);
	Long deletedNodePosition = nodeToDelete.getPosition();

	deleteNodesRecursively(nodeToDelete);

	BaseNode parent = nodeRepository.findOne(nodeToDelete.getParentId());
	List<BaseNode> children = nodeRepository.getChildren(parent.getId());
	if (children != null && !children.isEmpty()) {
	    for (BaseNode baseNode : children) {
		if (baseNode.getPosition() > deletedNodePosition) {
		    baseNode.setPosition(baseNode.getPosition() - 1);
		    nodeRepository.save(baseNode);
		}
	    }
	}
    }

    @RequestMapping(value="/nodes/{id}/copy", method = RequestMethod.POST, headers = "Accept=application/json")
    public void copy(@PathVariable("id") String id, @RequestBody NodeDTO nodeDTO) throws JSONException, MalformedURLException {
	BaseNode node = nodeRepository.findOne(id);
	node.setName(nodeDTO.getName());
	node.setDescription(nodeDTO.getDescription());

	copyNodesRecursively(node, node.getParentId());
    }

    public void copyNodesRecursively(BaseNode node, String parentId) throws JSONException, MalformedURLException {
	NodeDTO dto = EntityToDTO.toDTO(node);
	NodeDTO newNode = create(parentId, dto);

	String nodeType = node.getNodeType();
	if (nodeType != null
		&& (NodeType.FOLDER.name().equalsIgnoreCase(nodeType) || NodeType.PROJECT.name().equalsIgnoreCase(nodeType) || NodeType.ENTITY.name()
			.equalsIgnoreCase(nodeType))) {
	    List<BaseNode> children = getChildren(node.getId());
	    if (children != null && !children.isEmpty()) {
		for (BaseNode childNode : children) {
		    copyNodesRecursively(childNode, newNode.getId());
		}
	    }
	}
	// This is just a workaround added for now.
	if (nodeType != null && NodeType.FOLDER.name().equalsIgnoreCase(nodeType)) {
	    if (node.getGenericEntity() != null) {
		// TODO : genericEntityRepository.delete(node.getGenericEntity());
	    }

	} else if (nodeType != null && NodeType.ENTITY.name().equalsIgnoreCase(nodeType)) {
	    // TODO : genericEntityRepository.delete(node.getGenericEntity());
	}

    }

    public void deleteNodesRecursively(BaseNode node) {
	String nodeType = node.getNodeType();
	if (nodeType != null
		&& (NodeType.FOLDER.name().equalsIgnoreCase(nodeType) || NodeType.PROJECT.name().equalsIgnoreCase(nodeType) || NodeType.ENTITY.name()
			.equalsIgnoreCase(nodeType))) {
	    List<BaseNode> children = getChildren(node.getId());
	    if (children != null && !children.isEmpty()) {
		for (BaseNode childNode : children) {
		    deleteNodesRecursively(childNode);
		}
	    }
	}
	// This is just a workaround added for now.
	if (nodeType != null && NodeType.FOLDER.name().equalsIgnoreCase(nodeType)) {
	    if (node.getGenericEntity() != null) {
		genericEntityRepository.delete(node.getGenericEntity());
	    }

	} else if (nodeType != null && NodeType.ENTITY.name().equalsIgnoreCase(nodeType)) {
	    genericEntityRepository.delete(node.getGenericEntity());
	}
	nodeRepository.delete(node);
    }

    @RequestMapping(value="/nodes", method = RequestMethod.GET)
    public List<BaseNode> findAll() {
	logger.debug("Finding all nodes");

	return nodeRepository.findAll();
    }

    @RequestMapping(value="/nodes/{id}", method = RequestMethod.GET)
    public BaseNode findById(@PathVariable("id") String id) {
	logger.debug("Finding node by id: " + id);

	BaseNode baseNode = nodeRepository.findOne(id);
	baseNode.getConversation();

	return baseNode;
    }

    @RequestMapping(value="/nodes/{parentId}/children", method = RequestMethod.GET)
    public List<BaseNode> getChildren(@PathVariable("parentId") String parentId) {
	logger.debug("Finding children nodes");

	return nodeRepository.getChildren(parentId);
    }

    @RequestMapping(value="/nodes/{id}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public BaseNode update(@PathVariable("id") String id, @RequestBody NodeDTO updated) {
	logger.debug("Updating node with information: " + updated);

	BaseNode node = nodeRepository.findOne(updated.getId());

	if (updated.getName() != null) {
	    node.setName(updated.getName());
	}

	if (updated.getDescription() != null) {
	    node.setDescription(updated.getDescription());
	}

	if (updated.getStarred() != null) {
	    node.setStarred(updated.getStarred());
	}

	nodeRepository.save(node);

	return node;
    }

    public TreeNode getProjectTree(String id) {
	return getProjectTree(id, null, null);
    }

    // Get tree-structure for a project. Id parameter is the project-reference node-id.
    @RequestMapping(value="/nodes/{id}/tree", method = RequestMethod.GET)
    public TreeNode getProjectTree(@PathVariable("id") String id, @RequestParam(value = "search", required = false) String search,
                            @RequestParam(value = "sort", required = false) String sort) {
	// Note : There must be a better way of doing it. This method is written in a hurry.

	// Get project Id from the reference node
	BaseNode projectRefNode = nodeRepository.findOne(id);

	String projectId = projectRefNode.getProjectId();

	// Get the list of nodes for a project.
	List<BaseNode> listOfNodes = nodeRepository.searchNodesFromAProject(projectId, search != null ? search : "");

	// Creating a map of nodes with node-id as key
	Map<String, BaseNode> baseNodeMap = new HashMap<String, BaseNode>();
	Map<String, TreeNode> treeNodeMap = new HashMap<String, TreeNode>();
	TreeNode rootNode = null;
	TreeNode treeNode = null;
	TreeNode parentTreeNode = null;
	String methodType = "";

	for (BaseNode baseNode : listOfNodes) {
	    String nodeId = baseNode.getId();
	    baseNodeMap.put(nodeId, baseNode);

	    if (baseNode.getConversation() != null) {
		methodType = baseNode.getConversation().getApiRequest().getMethodType();
	    }
	    treeNode = TreeNodeBuilder.createTreeNode(nodeId, baseNode.getName(), baseNode.getDescription(), baseNode.getNodeType(),
		    baseNode.getStarred(), methodType, baseNode.getLastModifiedDate(), baseNode.getLastModifiedBy());
	    treeNode.setProjectId(projectId);
	    treeNodeMap.put(nodeId, treeNode);
	}

	for (BaseNode baseNode : listOfNodes) {
	    String nodeId = baseNode.getId();
	    String parentId = baseNode.getParentId();

	    treeNode = treeNodeMap.get(nodeId);

	    if (NodeType.PROJECT.name().equals(baseNode.getNodeType())) {
		// Identify root node for a project
		rootNode = treeNode;
	    } else {
		// Build parent node
		parentTreeNode = treeNodeMap.get(parentId);

		// Set parent tree node
		treeNode.setParent(parentTreeNode);

		// Add child node to the parent
		parentTreeNode.getChildren().add(treeNode);
	    }
	}

	if (search != null && !search.trim().equals("")) {
	    for (BaseNode baseNode : listOfNodes) {
		if (baseNode.getNodeType() != null && !NodeType.PROJECT.name().equals(baseNode.getNodeType())) {
		    TreeNode node = treeNodeMap.get(baseNode.getId());

		    if (node.getChildren().isEmpty()) {
			TreeNode parent = treeNodeMap.get(baseNode.getParentId());
			parent.getChildren().remove(node);
		    }
		}
	    }
	}

	if (sort != null) {
	    int order = 1;
	    if (sort.trim().charAt(0) == '-') {
		order = -1;
		sort = sort.substring(1);
	    }

	    sortTree(rootNode, sort, order);
	}
	return rootNode;
    }

    private void sortTree(TreeNode rootNode, String sort, final int order) {
	if (rootNode != null && rootNode.getChildren() != null) {

	    Comparator<TreeNode> comparator = null;

	    switch (sort) {
	    case "lastModified":
		comparator = new Comparator<TreeNode>() {

		    @Override
		    public int compare(TreeNode o1, TreeNode o2) {
			int val = 0;
			if (o1.getLastModifiedDate() != null && o2.getLastModifiedDate() != null) {
			    val = o1.getLastModifiedDate().compareTo(o2.getLastModifiedDate());
			} else if (o1.getLastModifiedDate() != null) {
			    val = 1;
			} else if (o2.getLastModifiedDate() != null) {
			    val = -1;
			}

			return order * val;
		    }
		};
		break;
	    case "name":
		comparator = new Comparator<TreeNode>() {

		    @Override
		    public int compare(TreeNode o1, TreeNode o2) {

			return order * o1.getName().compareTo(o2.getName());
		    }
		};
		break;

	    default:
		return;
	    }

	    sortTreeNodes(rootNode, comparator);

	}
    }

    private void sortTreeNodes(TreeNode rootNode, Comparator<TreeNode> comparator) {
	if (rootNode != null && rootNode.getChildren() != null) {
	    List<TreeNode> childs = rootNode.getChildren();
	    for (TreeNode node : childs) {
		sortTreeNodes(node, comparator);
	    }

	    Collections.sort(childs, comparator);

	    if (childs.size() > 0) {
		rootNode.setLastModifiedDate(childs.get(0).getLastModifiedDate());
	    }
	}
    }

    @RequestMapping(value="/workspaces/{workspaceId}/nodes/starred", method = RequestMethod.GET)
    public List<NodeDTO> findStarredNodes(@PathVariable("workspaceId") String workspaceId, @RequestParam(value = "page", required = false) Integer page,
                                   @RequestParam(value = "limit", required = false) Integer limit, @RequestParam(value = "search", required = false) String search,
                                   @RequestParam(value = "sortBy", required = false) String sortBy) {
	logger.debug("Finding starred nodes.");

	int pageNo = 0;
	if (page != null && page > 0) {
	    pageNo = page;
	}

	int numberOfRecords = 10;
	if (limit != null && limit > 0) {
	    numberOfRecords = limit;
	}
	Sort sort = new Sort(Direction.DESC, "lastModifiedDate");
	if ("name".equals(sortBy)) {
	    sort = new Sort(Direction.ASC, "name");
	} else if ("lastRun".equals(sortBy)) {
	    sort = new Sort(Direction.DESC, "lastModifiedDate");
	} else if ("nameDesc".equals(sortBy)) {
	    sort = new Sort(Direction.DESC, "name");
	}
	Pageable pageable = new PageRequest(pageNo, numberOfRecords, sort);

	Page<BaseNode> paginatedStarredNodes = nodeRepository.findStarredNodes(workspaceId, search != null ? search : "", pageable);

	List<BaseNode> starredNodes = paginatedStarredNodes.getContent();
	long totalElements = paginatedStarredNodes.getTotalElements();

	List<NodeDTO> response = new ArrayList<NodeDTO>();
	for (BaseNode item : starredNodes) {
	    response.add(EntityToDTO.toDTO(item));
	}

	System.out.println("totalElements : " + totalElements);
	return response;
    }

    @RequestMapping(value="/nodes/{id}/tags", method = RequestMethod.POST, headers = "Accept=application/json")
    public Boolean addTags(@PathVariable("id") String id, @RequestBody List<TagDTO> tagDTOs) {
	logger.debug("Adding the following tags: " + tagDTOs);

	BaseNode node = nodeRepository.findOne(id);

	List<Tag> tags = new ArrayList<Tag>();
	if (tagDTOs != null && !tagDTOs.isEmpty()) {
	    List<String> tagIds = new ArrayList<String>();
	    for (TagDTO tagDTO : tagDTOs) {
		tagIds.add(tagDTO.getId());
	    }
	    tags = (List<Tag>) tagRepository.findAll(tagIds);
	}
	node.setTags(tags);

	nodeRepository.save(node);
	return Boolean.TRUE;
    }

    @RequestMapping(value="/nodes/{id}/move", method = RequestMethod.POST, headers = "Accept=application/json")
    public void move(@PathVariable("id") String id, @RequestParam(value = "newParentId", required = true) String newParentId,
              @RequestParam(value = "newPosition", required = true) Long newPosition) {

	BaseNode node = nodeRepository.findOne(id);
	Long oldPosition = node.getPosition();

	BaseNode newParentNode = nodeRepository.findOne(newParentId);
	BaseNode oldParentNode = nodeRepository.findOne(node.getParentId());

	// update new folder
	List<BaseNode> newFolderChildren = nodeRepository.getChildren(newParentNode.getId());
	node.setParentId(newParentNode.getId());
	node.setPosition(newPosition);
	nodeRepository.save(node);
	if (newFolderChildren != null && !newFolderChildren.isEmpty()) {
	    for (BaseNode newFolderChild : newFolderChildren) {
		if (newFolderChild.getPosition() >= newPosition) {
		    newFolderChild.setPosition(newFolderChild.getPosition() + 1);
		    nodeRepository.save(newFolderChild);
		}
	    }
	}

	// update old folder
	List<BaseNode> oldFolderChildren = nodeRepository.getChildren(oldParentNode.getId());
	if (oldFolderChildren != null && !oldFolderChildren.isEmpty()) {
	    for (BaseNode oldFolderChild : oldFolderChildren) {
		if (oldFolderChild.getPosition() >= oldPosition) {
		    oldFolderChild.setPosition(oldFolderChild.getPosition() - 1);
		    nodeRepository.save(oldFolderChild);
		}
	    }
	}
    }
}
