package com.king.yyl.domain.apis.swagger.parameters;

public class PathParameter extends AbstractSerializableParameter<PathParameter> {

    public PathParameter() {
        super.setIn("path");
        super.setRequired(true);
    }
}
