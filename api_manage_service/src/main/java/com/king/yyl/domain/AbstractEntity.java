package com.king.yyl.domain;

import org.apache.commons.lang3.builder.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable, Cloneable, Comparable<Object> {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    private long version = 0;

    public String toString() {
	return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public boolean equals(Object o) {
	return EqualsBuilder.reflectionEquals(this, o);
    }

    public int compareTo(Object o) {
	return CompareToBuilder.reflectionCompare(this, o);
    }

    public int hashCode(Object o) {
	return HashCodeBuilder.reflectionHashCode(o);
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public long getVersion() {
	return version;
    }

    public void setVersion(long version) {
	this.version = version;
    }

}
