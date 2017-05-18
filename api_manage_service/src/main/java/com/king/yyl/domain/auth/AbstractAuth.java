package com.king.yyl.domain.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.king.yyl.domain.NamedEntity;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "authType")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "basic", value = BasicAuth.class),
    @JsonSubTypes.Type(name = "digest", value = DigestAuth.class),
    @JsonSubTypes.Type(name = "oauth2", value = OAuth2.class)}
)
public abstract class AbstractAuth extends NamedEntity {

    protected String authType;

    protected String username;

    protected String password;

    protected String authorizationUrl;

    protected String accessTokenUrl;

    protected String clientId;

    protected String clientSecret;

    protected String accessTokenLocation;

    protected String scopes;


    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAccessTokenLocation() {
        return accessTokenLocation;
    }

    public void setAccessTokenLocation(String accessTokenLocation) {
        this.accessTokenLocation = accessTokenLocation;
    }

    public String getScopes() {
        return scopes;
    }

    public void setScopes(String scopes) {
        this.scopes = scopes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractAuth that = (AbstractAuth) o;

        if (authType != null ? !authType.equals(that.authType) : that.authType != null) return false;
        if (username != null ? !username.equals(that.username) : that.username != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (authorizationUrl != null ? !authorizationUrl.equals(that.authorizationUrl) : that.authorizationUrl != null)
            return false;
        if (accessTokenUrl != null ? !accessTokenUrl.equals(that.accessTokenUrl) : that.accessTokenUrl != null)
            return false;
        if (clientId != null ? !clientId.equals(that.clientId) : that.clientId != null) return false;
        if (clientSecret != null ? !clientSecret.equals(that.clientSecret) : that.clientSecret != null) return false;
        if (accessTokenLocation != null ? !accessTokenLocation.equals(that.accessTokenLocation) : that.accessTokenLocation != null)
            return false;
        return scopes != null ? scopes.equals(that.scopes) : that.scopes == null;
    }

    @Override
    public int hashCode() {
        int result = authType != null ? authType.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (authorizationUrl != null ? authorizationUrl.hashCode() : 0);
        result = 31 * result + (accessTokenUrl != null ? accessTokenUrl.hashCode() : 0);
        result = 31 * result + (clientId != null ? clientId.hashCode() : 0);
        result = 31 * result + (clientSecret != null ? clientSecret.hashCode() : 0);
        result = 31 * result + (accessTokenLocation != null ? accessTokenLocation.hashCode() : 0);
        result = 31 * result + (scopes != null ? scopes.hashCode() : 0);
        return result;
    }
}
