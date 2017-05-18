package com.king.yyl.domain;

public abstract class NamedEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    protected String name;

    protected String description;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
}
