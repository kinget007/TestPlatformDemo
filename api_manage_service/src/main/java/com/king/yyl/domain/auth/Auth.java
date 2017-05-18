package com.king.yyl.domain.auth;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "authType")
@JsonSubTypes({
    @JsonSubTypes.Type(name = "basic", value = BasicAuth.class),
    @JsonSubTypes.Type(name = "digest", value = DigestAuth.class),
    @JsonSubTypes.Type(name = "oauth2", value = OAuth2.class)}
)
public interface Auth {

    String getAuthType();

    void setAuthType(String authType);

    String getUsername() ;

    void setUsername(String username) ;

    String getPassword() ;

    void setPassword(String password) ;

    String getAuthorizationUrl() ;

    void setAuthorizationUrl(String authorizationUrl) ;

    String getAccessTokenUrl() ;

    void setAccessTokenUrl(String accessTokenUrl) ;

    String getClientId() ;

    void setClientId(String clientId) ;

    String getClientSecret() ;

    void setClientSecret(String clientSecret) ;

    String getAccessTokenLocation() ;

    void setAccessTokenLocation(String accessTokenLocation) ;

    String getScopes() ;

    void setScopes(String scopes) ;
}
