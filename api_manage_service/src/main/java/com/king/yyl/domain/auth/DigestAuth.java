package com.king.yyl.domain.auth;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "digest")
public class DigestAuth extends AbstractAuth implements Auth{

    public DigestAuth(){
        super.setAuthType("digest");
    }

}
