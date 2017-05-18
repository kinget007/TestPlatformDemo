package com.king.yyl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A SwaggerApis.
 */

@Document(collection = "swagger_apis")
public class SwaggerApis implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("swagger_uri")
    private String swaggerUri;

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

    public SwaggerApis description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSwaggerUri() {
        return swaggerUri;
    }

    public SwaggerApis swaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
        return this;
    }

    public void setSwaggerUri(String swaggerUri) {
        this.swaggerUri = swaggerUri;
    }

    public String getProjectId() {
        return projectId;
    }

    public SwaggerApis projectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public SwaggerApis projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getEtc() {
        return etc;
    }

    public SwaggerApis etc(String etc) {
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
        SwaggerApis swaggerApis = (SwaggerApis) o;
        if (swaggerApis.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, swaggerApis.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SwaggerApis{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", swaggerUri='" + swaggerUri + "'" +
            ", projectId='" + projectId + "'" +
            ", projectDescription='" + projectDescription + "'" +
            ", etc='" + etc + "'" +
            '}';
    }
}
