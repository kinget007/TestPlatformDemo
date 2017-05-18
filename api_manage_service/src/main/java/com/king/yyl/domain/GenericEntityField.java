
package com.king.yyl.domain;


public class GenericEntityField extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private String name;

    // Note : It can be a simple data-type or another entity.
    // When an entity is renamed, this field needs to get updated, if reference found here.
    private String type;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
