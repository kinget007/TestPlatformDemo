package com.king.yyl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A JobJenkins.
 */

@Document(collection = "job_jenkins")
public class JobJenkins implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("url_jenkins")
    private String urlJenkins;

    @Field("server_id_jenkins")
    private String serverIdJenkins;

    @Field("folder_jenkins")
    private String folderJenkins;

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

    public JobJenkins description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlJenkins() {
        return urlJenkins;
    }

    public JobJenkins urlJenkins(String urlJenkins) {
        this.urlJenkins = urlJenkins;
        return this;
    }

    public void setUrlJenkins(String urlJenkins) {
        this.urlJenkins = urlJenkins;
    }

    public String getServerIdJenkins() {
        return serverIdJenkins;
    }

    public JobJenkins serverIdJenkins(String serverIdJenkins) {
        this.serverIdJenkins = serverIdJenkins;
        return this;
    }

    public void setServerIdJenkins(String serverIdJenkins) {
        this.serverIdJenkins = serverIdJenkins;
    }

    public String getFolderJenkins() {
        return folderJenkins;
    }

    public JobJenkins folderJenkins(String folderJenkins) {
        this.folderJenkins = folderJenkins;
        return this;
    }

    public void setFolderJenkins(String folderJenkins) {
        this.folderJenkins = folderJenkins;
    }

    public String getEtc() {
        return etc;
    }

    public JobJenkins etc(String etc) {
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
        JobJenkins jobJenkins = (JobJenkins) o;
        if (jobJenkins.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, jobJenkins.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "JobJenkins{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", urlJenkins='" + urlJenkins + "'" +
            ", serverIdJenkins='" + serverIdJenkins + "'" +
            ", folderJenkins='" + folderJenkins + "'" +
            ", etc='" + etc + "'" +
            '}';
    }
}
