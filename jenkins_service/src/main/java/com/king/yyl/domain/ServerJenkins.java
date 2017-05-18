package com.king.yyl.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ServerJenkins.
 */

@Document(collection = "server_jenkins")
public class ServerJenkins implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("url_jenkins")
    private String urlJenkins;

    @Field("login_jenkins")
    private String loginJenkins;

    @Field("pwd_jenkins")
    private String pwdJenkins;

    @Field("public_flag")
    private Boolean publicFlag;

    @Field("user_id")
    private String userId;

    @Field("user_name")
    private String userName;

    @Field("status")
    private String status;

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

    public ServerJenkins description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlJenkins() {
        return urlJenkins;
    }

    public ServerJenkins urlJenkins(String urlJenkins) {
        this.urlJenkins = urlJenkins;
        return this;
    }

    public void setUrlJenkins(String urlJenkins) {
        this.urlJenkins = urlJenkins;
    }

    public String getLoginJenkins() {
        return loginJenkins;
    }

    public ServerJenkins loginJenkins(String loginJenkins) {
        this.loginJenkins = loginJenkins;
        return this;
    }

    public void setLoginJenkins(String loginJenkins) {
        this.loginJenkins = loginJenkins;
    }

    public String getPwdJenkins() {
        return pwdJenkins;
    }

    public ServerJenkins pwdJenkins(String pwdJenkins) {
        this.pwdJenkins = pwdJenkins;
        return this;
    }

    public void setPwdJenkins(String pwdJenkins) {
        this.pwdJenkins = pwdJenkins;
    }

    public Boolean isPublicFlag() {
        return publicFlag;
    }

    public ServerJenkins publicFlag(Boolean publicFlag) {
        this.publicFlag = publicFlag;
        return this;
    }

    public void setPublicFlag(Boolean publicFlag) {
        this.publicFlag = publicFlag;
    }

    public String getUserId() {
        return userId;
    }

    public ServerJenkins userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public ServerJenkins userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public ServerJenkins status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEtc() {
        return etc;
    }

    public ServerJenkins etc(String etc) {
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
        ServerJenkins serverJenkins = (ServerJenkins) o;
        if (serverJenkins.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, serverJenkins.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ServerJenkins{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", urlJenkins='" + urlJenkins + "'" +
            ", loginJenkins='" + loginJenkins + "'" +
            ", pwdJenkins='" + pwdJenkins + "'" +
            ", publicFlag='" + publicFlag + "'" +
            ", userId='" + userId + "'" +
            ", userName='" + userName + "'" +
            ", status='" + status + "'" +
            ", etc='" + etc + "'" +
            '}';
    }
}
