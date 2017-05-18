package com.king.yyl.web.rest.util;

import com.king.yyl.domain.*;
import com.king.yyl.service.dto.*;

import java.util.ArrayList;
import java.util.List;

public class EntityToDTO {

    public static BaseDTO toDTO(BaseEntity entity) {
	if (entity == null)
	    return null;

	BaseDTO dto = new BaseDTO();

	copyBaseData(dto, entity);

	return dto;
    }

    public static BaseDTO toDTO(NamedEntity entity) {
	if (entity == null)
	    return null;

	BaseDTO dto = toDTO(entity);

	copyBaseData(dto, entity);

	return dto;
    }

    public static ConversationDTO toDTO(Conversation entity) {

	if (entity == null)
	    return null;

	ConversationDTO dto = new ConversationDTO();

	copyBaseData(dto, entity);
	dto.setApiRequestDTO(toDTO(entity.getApiRequest()));
	dto.setApiResponseDTO(toDTO(entity.getApiResponse()));
	dto.setDuration(entity.getDuration());

	return dto;
    }

    public static ApiRequestDTO toDTO(ApiRequest entity) {

	if (entity == null)
	    return null;

	ApiRequestDTO dto = new ApiRequestDTO();

	copyBaseData(dto, entity);

        dto.setApiUrl(entity.getApiUrl());
        dto.setMethodType(entity.getMethodType());
        dto.setParameters(entity.getParameters());
        dto.setAssertionDTO(toDTO(entity.getAssertion()));
        dto.setAuth(entity.getAuth());

	return dto;
    }

    public static AssertionDTO toDTO(Assertion entity) {

	if (entity == null)
	    return null;

	AssertionDTO dto = new AssertionDTO();

	dto.setStatusCode(entity.getStatusCode());
	dto.setResponseSize(entity.getResponseSize());
	dto.setResponseTime(entity.getResponseSize());
	dto.setBodyContentType(entity.getBodyContentType());
	dto.setBodyAssertDTOs(toListOfBodyAssertDTO(entity.getBodyAsserts()));

	return dto;
    }

    public static BodyAssertDTO toDTO(BodyAssert entity) {

	if (entity == null)
	    return null;

	BodyAssertDTO dto = new BodyAssertDTO();

	dto.setPropertyName(entity.getPropertyName());
	dto.setComparator(entity.getComparator());
	dto.setExpectedValue(entity.getExpectedValue());
	dto.setActualValue(entity.getActualValue());
	dto.setSuccess(entity.isSuccess());

	return dto;
    }

    public static ApiResponseDTO toDTO(ApiResponse entity) {

	if (entity == null)
	    return null;

	ApiResponseDTO dto = new ApiResponseDTO();

	copyBaseData(dto, entity);

	dto.setBody(entity.getBody());
	dto.setHeaderParameters(entity.getHeaders());
//	dto.setResponse(entity.getResponse());

	return dto;
    }

    public static NodeDTO toDTO(BaseNode entity) {

	if (entity == null)
	    return null;

	NodeDTO dto = new NodeDTO();

	copyBaseData(dto, entity);

	dto.setNodeType(entity.getNodeType());
	dto.setParentId(entity.getParentId());
	dto.setProjectId(entity.getProjectId());
	dto.setPosition(entity.getPosition());
	dto.setStarred(entity.getStarred());
	dto.setMethod(entity.getMethod());
	dto.setTags(toListOfTagDTO(entity.getTags()));
	dto.setConversationDTO(toDTO(entity.getConversation()));
	dto.setGenericEntityDTO(toDTO(entity.getGenericEntity()));
	if(entity.getConversation()!=null && entity.getConversation().getApiRequest()!=null){
	    dto.setApiURL(entity.getConversation().getApiRequest().getApiUrl());
	}

	return dto;

    }

    public static GenericEntityDTO toDTO(GenericEntity entity) {

	if (entity == null)
	    return null;

	GenericEntityDTO dto = new GenericEntityDTO();

	copyBaseData(dto, entity);

	dto.setEntityDataList(toListOfGenericEntityDataDTO(entity.getEntityDataList()));
	dto.setFields(toListOfGenericEntityFieldDTO(entity.getFields()));

	return dto;

    }

    public static GenericEntityFieldDTO toDTO(GenericEntityField entity) {

	if (entity == null)
	    return null;

	GenericEntityFieldDTO dto = new GenericEntityFieldDTO();

	dto.setName(entity.getName());
	dto.setType(entity.getType());

	return dto;
    }

    public static GenericEntityDataDTO toDTO(GenericEntityData entity) {

	if (entity == null)
	    return null;

	GenericEntityDataDTO dto = new GenericEntityDataDTO();

	copyBaseData(dto, entity);

	dto.setData(entity.getData());

	return dto;

    }

    public static TagDTO toDTO(Tag entity) {

	if (entity == null)
	    return null;

	TagDTO dto = new TagDTO();

	copyBaseData(dto, entity);

	dto.setColor(toDTO(entity.getColor()));
	dto.setWorkspace(toDTO(entity.getWorkspace()));

	return dto;
    }

    private static ColorDTO toDTO(Color entity) {

	if (entity == null)
	    return null;

	ColorDTO dto = new ColorDTO();

	dto.setDisplayName(entity.getDisplayName());
	dto.setColorCode(entity.getColorCode());

	return dto;
    }

    public static WorkspaceDTO toDTO(Workspace entity) {

	if (entity == null)
	    return null;

	WorkspaceDTO dto = new WorkspaceDTO();

	copyBaseData(dto, entity);

	dto.setProjects(toListOfProjectDTO(entity.getProjects()));

	return dto;
    }

    public static ProjectDTO toDTO(Project entity) {

	if (entity == null)
	    return null;

	ProjectDTO dto = new ProjectDTO();
	dto.setProjectRef(toDTO(entity.getProjectRef()));

	return dto;
    }

    public static List<BodyAssertDTO> toListOfBodyAssertDTO(List<BodyAssert> entity) {

	if (entity == null)
	    return null;

	List<BodyAssertDTO> dto = new ArrayList<BodyAssertDTO>();
	for (BodyAssert item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<GenericEntityFieldDTO> toListOfGenericEntityFieldDTO(List<GenericEntityField> entity) {

	if (entity == null)
	    return null;

	List<GenericEntityFieldDTO> dto = new ArrayList<GenericEntityFieldDTO>();
	for (GenericEntityField item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<GenericEntityDataDTO> toListOfGenericEntityDataDTO(List<GenericEntityData> entity) {

	if (entity == null)
	    return null;

	List<GenericEntityDataDTO> dto = new ArrayList<GenericEntityDataDTO>();
	for (GenericEntityData item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<TagDTO> toListOfTagDTO(List<Tag> entity) {

	if (entity == null)
	    return null;

	List<TagDTO> dto = new ArrayList<TagDTO>();
	for (Tag item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    public static List<ProjectDTO> toListOfProjectDTO(List<Project> entity) {

	if (entity == null)
	    return null;

	List<ProjectDTO> dto = new ArrayList<ProjectDTO>();
	for (Project item : entity) {
	    dto.add(toDTO(item));
	}

	return dto;
    }

    private static void copyBaseData(BaseDTO dto, BaseEntity entity) {
	dto.setId(entity.getId());
	dto.setCreatedDate(entity.getCreatedDate());
	dto.setCreatedBy(entity.getCreatedBy());
	dto.setLastModifiedDate(entity.getLastModifiedDate());
	dto.setLastModifiedBy(entity.getLastModifiedBy());
	dto.setStatus(entity.getStatus());
    }

    private static void copyBaseData(BaseDTO dto, NamedEntity entity) {

	dto.setId(entity.getId());
	dto.setCreatedDate(entity.getCreatedDate());
	dto.setCreatedBy(entity.getCreatedBy());
	dto.setLastModifiedDate(entity.getLastModifiedDate());
	dto.setLastModifiedBy(entity.getLastModifiedBy());
	dto.setStatus(entity.getStatus());

	dto.setName(entity.getName());
	dto.setDescription(entity.getDescription());
    }

}
