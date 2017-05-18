package com.king.yyl.domain.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "oauth2")
public class OAuth2 extends AbstractAuth implements Auth{
    public OAuth2(){
        super.setAuthType("oauth2");
    }
}
