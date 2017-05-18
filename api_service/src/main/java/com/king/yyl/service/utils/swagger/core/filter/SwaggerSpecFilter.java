package com.king.yyl.service.utils.swagger.core.filter;

import com.king.yyl.domain.apis.swagger.Model;
import com.king.yyl.domain.apis.swagger.Operation;
import com.king.yyl.domain.apis.swagger.parameters.Parameter;
import com.king.yyl.domain.apis.swagger.properties.Property;
import com.king.yyl.service.utils.swagger.core.model.ApiDescription;

import java.util.List;
import java.util.Map;

public interface SwaggerSpecFilter {
    boolean isOperationAllowed(
        Operation operation,
        ApiDescription api,
        Map<String, List<String>> params,
        Map<String, String> cookies,
        Map<String, List<String>> headers);

    boolean isParamAllowed(
        Parameter parameter,
        Operation operation,
        ApiDescription api,
        Map<String, List<String>> params,
        Map<String, String> cookies,
        Map<String, List<String>> headers);

    boolean isPropertyAllowed(
        Model model,
        Property property,
        String propertyName,
        Map<String, List<String>> params,
        Map<String, String> cookies,
        Map<String, List<String>> headers);
}
