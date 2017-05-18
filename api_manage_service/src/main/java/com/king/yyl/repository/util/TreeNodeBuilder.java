
package com.king.yyl.repository.util;

import com.king.yyl.web.rest.util.TreeNode;

import java.util.Date;

public class TreeNodeBuilder {

    public static TreeNode createTreeNode(String nodeId, String nodeName, String nodeDesc, String nodeType,Boolean starred, String method, Date lastModifiedDate, String lastModifiedBy) {
	TreeNode treeNode = new TreeNode();
	treeNode.setId(nodeId);
	treeNode.setName(nodeName);
	treeNode.setDescription(nodeDesc);
	treeNode.setNodeType(nodeType);
	treeNode.setStarred(starred);
	treeNode.setMethod(method);
	treeNode.setLastModifiedDate(lastModifiedDate);
	treeNode.setLastModifiedBy(lastModifiedBy);
	return treeNode;
    }
}
