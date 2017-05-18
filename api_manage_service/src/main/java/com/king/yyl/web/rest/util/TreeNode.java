
package com.king.yyl.web.rest.util;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.king.yyl.domain.BaseNode;

import java.util.ArrayList;
import java.util.List;

//NOTE : TreeNode is NOT an Entity.
public class TreeNode extends BaseNode {
    private static final long serialVersionUID = 1L;

    @JsonBackReference
    private TreeNode parent;

    @JsonManagedReference
    private List<TreeNode> children = new ArrayList<TreeNode>();

    public TreeNode getParent() {
	return parent;
    }

    public void setParent(TreeNode parent) {
	this.parent = parent;
    }

    public List<TreeNode> getChildren() {
	return children;
    }

    public void setChildren(List<TreeNode> children) {
	this.children = children;
    }
}
