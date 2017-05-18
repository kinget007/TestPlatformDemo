
package com.king.yyl.service.dto;

import java.util.List;

public class GenericEntityDTO extends BaseDTO {

    private List<GenericEntityFieldDTO> fields;

    private List<GenericEntityDataDTO> entityDataList;

    public List<GenericEntityFieldDTO> getFields() {
	return fields;
    }

    public void setFields(List<GenericEntityFieldDTO> fields) {
	this.fields = fields;
    }

    public List<GenericEntityDataDTO> getEntityDataList() {
	return entityDataList;
    }

    public void setEntityDataList(List<GenericEntityDataDTO> entityDataList) {
	this.entityDataList = entityDataList;
    }
}
