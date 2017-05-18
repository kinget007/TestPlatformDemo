package com.king.yyl.domain.apis.swagger.parameters;

public class FormParameter extends AbstractSerializableParameter<FormParameter> {

    public FormParameter() {
        super.setIn("formData");
    }

    @Override
    protected String getDefaultCollectionFormat() {
        return "multi";
    }
}
