package com.king.yyl.service.utils.swagger.core.config;

import com.king.yyl.domain.apis.swagger.Swagger;

public interface SwaggerConfig {

    Swagger configure(Swagger swagger);

    String getFilterClass();
}
