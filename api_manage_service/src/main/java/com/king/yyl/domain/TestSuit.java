package com.king.yyl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TestSuit.
 */

@Document(collection = "test_suit")
public class TestSuit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("swagger_uri")
    private String swaggerUri;

    @Field("depend_on")
    private String dependOn;

    @Field("project_id")
    private String projectId;

    @Field("project_description")
    private String projectDescription;

    @Field("etc")
    private String etc;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public TestSuit description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwaggerUri() {
        return swaggerUri;
    }

    public TestSuit swaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
        return this;
    }

    public void setSwaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
    }

    public String getDependOn() {
        return dependOn;
    }

    public TestSuit dependOn(String dependOn) {
        this.dependOn = dependOn;
        return this;
    }

    public void setDependOn(String dependOn) {
        this.dependOn = dependOn;
    }

    public String getProjectId() {
        return projectId;
    }

    public TestSuit projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public TestSuit projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getEtc() {
        return etc;
    }

    public TestSuit etc(String etc) {
        this.etc = etc;
        return this;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TestSuit testSuit = (TestSuit) o;
        if (testSuit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, testSuit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TestSuit{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", swaggerUri='" + swaggerUri + "'" +
            ", dependOn='" + dependOn + "'" +
            ", projectId='" + projectId + "'" +
            ", projectDescription='" + projectDescription + "'" +
            ", etc='" + etc + "'" +
            '}';
    }
}
