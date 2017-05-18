package com.king.yyl.service.utils.swagger.parser.processors;

import com.king.yyl.domain.apis.swagger.Response;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.apis.swagger.properties.Property;
import com.king.yyl.service.utils.swagger.parser.ResolverCache;

public class ResponseProcessor {

    private final PropertyProcessor propertyProcessor;

    public ResponseProcessor(ResolverCache cache, Swagger swagger) {
        propertyProcessor = new PropertyProcessor(cache, swagger);
    }

    public void processResponse(Response response) {
        //process the response body
        final Property schema = response.getSchema();

        if (schema != null) {
            propertyProcessor.processProperty(schema);
        }

        /* intentionally ignoring the response headers, even those these were modelled as a
         Map<String, Property> they should never have a $ref because what does it mean to have a
         complex object in an HTTP header?
          */

    }
}
