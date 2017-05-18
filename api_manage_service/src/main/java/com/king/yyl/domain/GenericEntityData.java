
package com.king.yyl.domain;

public class GenericEntityData extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * Stores entity data in JSON format with key as field names.
     */
    private String data;

    public String getData() {
	return data;
    }

    public void setData(String data) {
	this.data = data;
    }
}
