package com.king.yyl.domain.parameters;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "path")
public class PathParameter extends AbstractParameter implements Parameter {

    public PathParameter() {
        super.setIn("path");
        super.setRequired(true);
    }
}
