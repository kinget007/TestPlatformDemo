package com.king.yyl.domain;

import com.king.yyl.service.enums.StatusType;

import java.util.Date;

public abstract class BaseEntity extends AbstractEntity {
    private static final long serialVersionUID = 1L;

    private Date createdDate;

    private String createdBy;//userId

    private Date lastModifiedDate;

    private String lastModifiedBy;//userId

    private String status = StatusType.ACTIVE.name();

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
	return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
	this.lastModifiedDate = lastModifiedDate;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }
}
