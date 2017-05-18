
package com.king.yyl.domain;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class GenericEntity extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private List<GenericEntityField> fields = new ArrayList<GenericEntityField>();

    @DBRef
    private List<GenericEntityData> entityDataList = new ArrayList<GenericEntityData>();

    private String baseNodeId;

    public List<GenericEntityField> getFields() {
	return fields;
    }

    public void setFields(List<GenericEntityField> fields) {
	this.fields = fields;
    }

    public List<GenericEntityData> getEntityDataList() {
	return entityDataList;
    }

    public void setEntityDataList(List<GenericEntityData> entityDataList) {
	this.entityDataList = entityDataList;
    }

    public String getBaseNodeId() {
	return baseNodeId;
    }

    public void setBaseNodeId(String baseNodeId) {
	this.baseNodeId = baseNodeId;
    }

}
