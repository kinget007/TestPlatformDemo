package com.king.yyl.web.rest.util;

import com.king.yyl.domain.*;
import com.king.yyl.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class DTOToEntity {

    public static Assertion toEntity(AssertionDTO dto) {

        if (dto == null) {
            return null;
        }

        Assertion assertion = new Assertion();

        assertion.setBodyContentType(dto.getBodyContentType());
        assertion.setBodyAsserts(toEntity(dto.getBodyAssertDTOs()));
        assertion.setResponseSize(dto.getResponseSize());
        assertion.setStatusCode(dto.getStatusCode());
        assertion.setResponseTime(dto.getResponseTime());


        return assertion;
    }


    public static List<BodyAssert> toEntity(List<BodyAssertDTO> dtos) {

        List<BodyAssert> bodyAsserts = new ArrayList<>();
        if (dtos.size() > 0) {
            for (int i = 0; i < dtos.size(); i++) {
                BodyAssert bodyAssert = new BodyAssert();

                bodyAssert.setActualValue(dtos.get(i).getActualValue());
                bodyAssert.setComparator(dtos.get(i).getComparator());
                bodyAssert.setExpectedValue(dtos.get(i).getExpectedValue());
                bodyAssert.setPropertyName(dtos.get(i).getPropertyName());
                bodyAsserts.add(bodyAssert);
            }
        }

        return bodyAsserts;
    }


    public static ApiRequest toEntity(ApiRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        ApiRequest apiRequest = new ApiRequest();

        copyBaseData(apiRequest, dto);
        apiRequest.setApiUrl(dto.getApiUrl());
        apiRequest.setAssertion(toEntity(dto.getAssertionDTO()));
        apiRequest.setAuth(dto.getAuth());
        apiRequest.setParameters(dto.getParameters());

        return apiRequest;
    }


    public static ApiResponse toEntity(ApiResponseDTO dto) {
        if (dto == null) {
            return null;
        }
        ApiResponse apiResponse = new ApiResponse();
        copyBaseData(apiResponse, dto);
        apiResponse.setBody(dto.getBody());
        apiResponse.setHeaders(dto.getHeaderParameters());
        return apiResponse;

    }

    private static void copyBaseData(BaseEntity entity, BaseDTO dto) {

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setStatus(dto.getStatus());
    }

    private static void copyBaseData(NamedEntity entity, BaseDTO dto) {

        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setLastModifiedDate(dto.getLastModifiedDate());
        entity.setLastModifiedBy(dto.getLastModifiedBy());
        entity.setCreatedDate(dto.getCreatedDate());
        entity.setCreatedBy(dto.getCreatedBy());
        entity.setStatus(dto.getStatus());

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
    }

}
