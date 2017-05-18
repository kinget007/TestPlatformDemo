package com.king.yyl.service.utils.swagger.parser;

import com.king.yyl.service.utils.swagger.parser.processors.DefinitionsProcessor;
import com.king.yyl.service.utils.swagger.parser.processors.OperationProcessor;
import com.king.yyl.service.utils.swagger.parser.processors.PathsProcessor;
import com.king.yyl.domain.apis.swagger.Operation;
import com.king.yyl.domain.apis.swagger.Path;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.apis.swagger.auth.AuthorizationValue;
//import com.king.yyl.service.utils.swagger.parser.processors.DefinitionsProcessor;
//import com.king.yyl.service.utils.swagger.parser.processors.OperationProcessor;
//import com.king.yyl.service.utils.swagger.parser.processors.PathsProcessor;

import java.util.List;

/**
 *
 */
public class SwaggerResolver {
    private final Swagger swagger;
    private final ResolverCache cache;
    private final PathsProcessor pathProcessor;
    private final DefinitionsProcessor definitionsProcessor;
    private final OperationProcessor operationsProcessor;

    public SwaggerResolver(Swagger swagger, List<AuthorizationValue> auths, String parentFileLocation) {
        this.swagger = swagger;
        this.cache = new ResolverCache(swagger, auths, parentFileLocation);
        definitionsProcessor = new DefinitionsProcessor(cache, swagger);
        pathProcessor = new PathsProcessor(cache, swagger);
        operationsProcessor = new OperationProcessor(cache, swagger);
    }

    public SwaggerResolver(Swagger swagger,  List<AuthorizationValue> auths) {
        this(swagger, auths, null);
    }

    public Swagger resolve() {
        if (swagger == null) {
            return null;
        }

        pathProcessor.processPaths();
        definitionsProcessor.processDefinitions();

        if(swagger.getPaths() != null) {
            for(String pathname : swagger.getPaths().keySet()) {
                Path path = swagger.getPaths().get(pathname);
                if(path.getOperations() != null) {
                    for(Operation operation : path.getOperations()) {
                        operationsProcessor.processOperation(operation);
                    }
                }
            }
        }

        return swagger;
    }
}
