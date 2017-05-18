package com.king.yyl.web.rest;

import com.king.yyl.domain.ApiRequest;
import com.king.yyl.domain.ApiResponse;
import com.king.yyl.domain.Assertion;
import com.king.yyl.domain.Conversation;
import com.king.yyl.repository.ApiRequestRepository;
import com.king.yyl.repository.ApiResponseRepository;
import com.king.yyl.repository.AssertionRepository;
import com.king.yyl.repository.ConversationRepository;
import com.king.yyl.repository.util.ConversationConverter;
import com.king.yyl.service.dto.ApiRequestDTO;
import com.king.yyl.service.dto.ApiResponseDTO;
import com.king.yyl.service.dto.ConversationDTO;
import com.king.yyl.service.dto.PaginatedResponse;
import com.king.yyl.web.rest.util.DTOToEntity;
import com.king.yyl.web.rest.util.EntityToDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ConversationResource {
    Logger logger = LoggerFactory.getLogger(ConversationResource.class);

    @Inject
    private ConversationRepository conversationRepository;

    @Inject
    private ApiRequestRepository apiRequestRepository;

    @Inject
    private ApiResponseRepository apiResponseRepository;

    @Inject
    private AssertionRepository assertionRepository;

    @RequestMapping(value="/conversations", method = RequestMethod.POST, headers = "Accept=application/json")
    public ConversationDTO create(@RequestBody ConversationDTO conversationDTO) {
        logger.debug("Creating a new item with information: " + conversationDTO);

        Conversation conversation = new Conversation();
        ApiRequest apiRequest = new ApiRequest();
        ApiResponse apiResponse = new ApiResponse();

        apiRequest.setApiUrl(conversationDTO.getApiRequestDTO().getApiUrl());
        apiRequest.setMethodType(conversationDTO.getApiRequestDTO().getMethodType());

        Assertion assertion = assertionRepository.save(DTOToEntity.toEntity(conversationDTO.getApiRequestDTO().getAssertionDTO()));
        apiRequest.setAssertion(assertion);

        apiRequest.setAuth(conversationDTO.getApiRequestDTO().getAuth());
        apiRequest.setParameters(conversationDTO.getApiRequestDTO().getParameters());

        apiResponse.setBody(conversationDTO.getApiResponseDTO().getBody());

        apiRequest = apiRequestRepository.save(apiRequest);

        apiResponse = apiResponseRepository.save(apiResponse);

        conversation.setApiRequest(apiRequest);

        conversation.setApiResponse(apiResponse);
        conversation.setName(conversationDTO.getName());
        conversation.setDescription(conversationDTO.getDescription());
        conversation.setWorkspaceId(conversationDTO.getWorkspaceId());

        conversation.setCreatedDate(new Date());
        conversation.setLastModifiedDate(new Date());

        if(conversationDTO.getNodeDTO() != null ){
            conversation.setNodeId(conversationDTO.getNodeDTO().getId());

        }

        conversation = conversationRepository.save(conversation);
        conversation.getApiRequest().setConversationId(conversation.getId());
        apiRequestRepository.save(conversation.getApiRequest());

        ConversationDTO result = EntityToDTO.toDTO(conversation);

        return result;
//        return ResponseEntity.created(new URI("/api/conversations"))
//            .headers(HeaderUtil.createEntityCreationAlert("apiSendInfo", conversationDTO.getId().toString()))
//            .body(result);
    }

    @RequestMapping(value="/conversations/{conversationId}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public Conversation delete(@PathVariable("conversationId") String conversationId) {
        logger.debug("Deleting item with id: " + conversationId);

        Conversation deleted = conversationRepository.findOne(conversationId);

        conversationRepository.delete(deleted);

        return deleted;
    }

    @RequestMapping(value="/conversations", method = RequestMethod.GET)
    public PaginatedResponse<ConversationDTO> findAll(@RequestParam(value = "workspaceId", required = false) String workspaceId,
                                                      @RequestParam(value = "search", required = false) String search,
                                                      @RequestParam(value = "page", required = false) Integer page,
                                                      @RequestParam(value = "limit", required = false) Integer limit,
                                                      @RequestParam(value = "sortBy", required = false) String sortBy) {
        logger.debug("Finding all items");

        int pageNo = 0;
        if (page != null && page > 0) {
            pageNo = page;
        }

        int numberOfRecords = 10;
        if (limit != null && limit > 0) {
            numberOfRecords = limit;
        }

        Sort sort = new Sort(Direction.DESC, "lastModifiedDate");
        if("name".equals(sortBy)){
            sort = new Sort(Direction.ASC, "name");
        } else if ("lastRun".equals(sortBy)){
            sort = new Sort(Direction.DESC, "lastModifiedDate");
        }else if ("nameDesc".equals(sortBy)){
            sort = new Sort(Direction.DESC, "name");
        }

        Pageable pageable = new PageRequest(pageNo, numberOfRecords, sort);
        Page<Conversation> result = conversationRepository.findConversationsFromWorkspaceByName(workspaceId, search != null ? search : "", pageable);

        List<Conversation> content = result.getContent();

        List<ConversationDTO> responseContent = new ArrayList<ConversationDTO>();
        for(Conversation item : content){
            responseContent.add(EntityToDTO.toDTO(item));
        }

        PaginatedResponse<ConversationDTO> response = new PaginatedResponse<ConversationDTO>();
        response.setData(responseContent);
        response.setLimit(numberOfRecords);
        response.setPage(pageNo);
        response.setTotalElements(result.getTotalElements());
        response.setTotalPages(result.getTotalPages());

        for (Conversation item : content) {
            ApiRequest apiRequest = item.getApiRequest();
            logger.debug(apiRequest.getApiUrl());
        }
        return response;
    }

    @RequestMapping(value="/conversations/{conversationId}", method = RequestMethod.GET)
    public Conversation findById(@PathVariable("conversationId") String conversationId) {
        logger.debug("Finding item by id: " + conversationId);

        return conversationRepository.findOne(conversationId);
    }

    @RequestMapping(value="/conversations/{conversationId}", method = RequestMethod.PUT, headers = "Accept=application/json")
    public Conversation update(@PathVariable("conversationId") String conversationId, @RequestBody ConversationDTO conversationDTO) {
        Conversation dbConversation = conversationRepository.findOne(conversationDTO.getId());

        ApiRequestDTO apiRequestDTO = conversationDTO.getApiRequestDTO();
        ApiResponseDTO apiResponseDTO = new ApiResponseDTO();

        Conversation conversation = ConversationConverter.convertToEntity(apiRequestDTO, apiResponseDTO);
        conversation.setId(dbConversation.getId());

        conversation.setName(conversationDTO.getName());
        conversation.setDescription(conversationDTO.getDescription());
        conversation.setCreatedDate(new Date());
        conversation.setLastModifiedDate(new Date());
        if(conversationDTO.getNodeDTO() != null )
            conversation.setNodeId(conversationDTO.getNodeDTO().getId());

        ApiRequest apiRequest = conversation.getApiRequest();
        ApiRequest dbApiRequest = dbConversation.getApiRequest();
        if (dbApiRequest != null) {
            apiRequest.setId(dbApiRequest.getId());
        }
        apiRequest.setConversationId(conversation.getId());
        apiRequestRepository.save(apiRequest);

        ApiResponse rfResponse = conversation.getApiResponse();
        ApiResponse dbRfResponse = dbConversation.getApiResponse();
        if(dbRfResponse != null){
            rfResponse.setId(dbRfResponse.getId());
        }
        apiResponseRepository.save(rfResponse);

        conversation = conversationRepository.save(conversation);

        return conversation;
    }
}
