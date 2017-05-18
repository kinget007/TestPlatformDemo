package com.king.yyl.domain.parameters;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "cookie")
public class CookieParameter extends AbstractParameter implements Parameter {

    public CookieParameter() {
        super.setIn("cookie");
    }
}
