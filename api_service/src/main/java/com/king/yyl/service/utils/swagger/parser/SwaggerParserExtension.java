package com.king.yyl.service.utils.swagger.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.king.yyl.service.utils.swagger.parser.util.SwaggerDeserializationResult;
import com.king.yyl.domain.apis.swagger.Swagger;
import com.king.yyl.domain.apis.swagger.auth.AuthorizationValue;

import java.io.IOException;
import java.util.List;

public interface SwaggerParserExtension {
    SwaggerDeserializationResult readWithInfo(JsonNode node);
    SwaggerDeserializationResult readWithInfo(String location, List<AuthorizationValue> auths);
    Swagger read(String location, List<AuthorizationValue> auths) throws IOException;
    Swagger read(JsonNode node) throws IOException;
}
