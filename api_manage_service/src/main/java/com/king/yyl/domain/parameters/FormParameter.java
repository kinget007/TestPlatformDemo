package com.king.yyl.domain.parameters;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName(value = "form")
public class FormParameter extends AbstractParameter implements Parameter {

    public FormParameter() {
        super.setIn("form");
    }

}
