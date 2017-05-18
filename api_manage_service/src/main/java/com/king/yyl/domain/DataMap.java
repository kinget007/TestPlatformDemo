
package com.king.yyl.domain;

public class DataMap extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private String dataKey;

    private String value;

    private String type;

    public String getDataKey() {
	return dataKey;
    }

    public void setDataKey(String dataKey) {
	this.dataKey = dataKey;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

}
