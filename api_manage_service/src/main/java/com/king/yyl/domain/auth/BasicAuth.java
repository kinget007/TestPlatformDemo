package com.king.yyl.domain.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "basic")
public class BasicAuth extends AbstractAuth implements Auth{

    public BasicAuth(){
        super.setAuthType("basic");
    }

}
