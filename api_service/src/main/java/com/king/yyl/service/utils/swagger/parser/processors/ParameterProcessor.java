package com.king.yyl.service.utils.swagger.parser.processors;

import com.king.yyl.domain.apis.swagger.Model;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.apis.swagger.parameters.BodyParameter;
import com.king.yyl.domain.apis.swagger.parameters.Parameter;
import com.king.yyl.domain.apis.swagger.parameters.RefParameter;
import com.king.yyl.service.utils.swagger.parser.ResolverCache;

import java.util.ArrayList;
import java.util.List;


public class ParameterProcessor {

    private final ResolverCache cache;
    private final ModelProcessor modelProcessor;


    public ParameterProcessor(ResolverCache cache, Swagger swagger) {
        this.cache = cache;
        this.modelProcessor = new ModelProcessor(cache, swagger);
    }

    public List<Parameter> processParameters(List<Parameter> parameters) {

        if (parameters == null) {
            return null;
        }

        final List<Parameter> processedPathLevelParameters = new ArrayList<>();

        for (Parameter parameter : parameters) {

            if (parameter instanceof RefParameter) {
                RefParameter refParameter = (RefParameter) parameter;
                final Parameter resolvedParameter = cache.loadRef(refParameter.get$ref(), refParameter.getRefFormat(), Parameter.class);
                parameter = resolvedParameter;
            }

            if (parameter instanceof BodyParameter) {
                BodyParameter bodyParameter = (BodyParameter) parameter;
                final Model schema = bodyParameter.getSchema();
                modelProcessor.processModel(schema);
            }

            processedPathLevelParameters.add(parameter);

        }
        return processedPathLevelParameters;
    }
}
