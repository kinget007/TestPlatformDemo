package com.king.yyl.service.utils.swagger.core.util;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.king.yyl.domain.apis.swagger.Model;
import com.king.yyl.domain.apis.swagger.Path;
import com.king.yyl.domain.apis.swagger.Response;
import com.king.yyl.domain.apis.swagger.auth.SecuritySchemeDefinition;
import com.king.yyl.domain.apis.swagger.parameters.Parameter;
import com.king.yyl.domain.apis.swagger.properties.Property;


public class DeserializationModule extends SimpleModule {

    public DeserializationModule(boolean includePathDeserializer,
                                 boolean includeResponseDeserializer) {

        if (includePathDeserializer) {
            this.addDeserializer(Path.class, new PathDeserializer());
        }
        if (includeResponseDeserializer) {
            this.addDeserializer(Response.class, new ResponseDeserializer());
        }

        this.addDeserializer(Property.class, new PropertyDeserializer());
        this.addDeserializer(Model.class, new ModelDeserializer());
        this.addDeserializer(Parameter.class, new ParameterDeserializer());
        this.addDeserializer(SecuritySchemeDefinition.class, new SecurityDefinitionDeserializer());
    }

    public DeserializationModule() {
        this(true, true);
    }
}
