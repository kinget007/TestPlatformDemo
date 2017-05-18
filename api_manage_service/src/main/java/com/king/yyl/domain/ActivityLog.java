
package com.king.yyl.domain;

public class ActivityLog extends NamedEntity {
    private static final long serialVersionUID = 1L;

    private String type; // e.g. CONVERSATION

    private String data;

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }
}
