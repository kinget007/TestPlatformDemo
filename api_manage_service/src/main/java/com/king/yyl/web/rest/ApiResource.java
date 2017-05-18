package com.king.yyl.web.rest;

import com.king.yyl.domain.*;
import com.king.yyl.repository.*;
import com.king.yyl.service.dto.*;
import com.king.yyl.service.enums.NodeType;
import com.king.yyl.service.handler.AssertHandler;
import com.king.yyl.service.handler.http.GenericHandler;
import com.king.yyl.web.rest.util.DTOToEntity;
import com.king.yyl.web.rest.util.EntityToDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class ApiResource {
    Logger logger = LoggerFactory.getLogger(ApiResource.class);

    @Autowired
    GenericHandler genericHandler;

    @Autowired
    private AssertHandler assertHandler;

    @Autowired
    private EnvironmentResource environmentController;

    @Inject
    private NodeRepository nodeRepository;

    @Inject
    private ConversationRepository conversationRepository;

    @Inject
    private ApiRequestRepository apiRequestRepository;

    @Autowired
    private ApiResponseRepository apiResponseRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @RequestMapping(value = "/processor", method = RequestMethod.POST, headers = "Accept=application/json")
    public ConversationDTO requestProcessor(@RequestBody ApiRequestDTO apiRequestDTO) throws URISyntaxException {

        ApiResponseDTO apiResponseDTO = genericHandler.processHttpRequest(apiRequestDTO);
        String nodeId = "";

        if (apiRequestDTO == null) {
            return null;
        } else if (apiRequestDTO.getId() == null || apiRequestDTO.getConversationId() == null || apiRequestDTO.getWorkspaceId() == null) {
            //conversation not exist in db
            ConversationDTO conversationDTO = new ConversationDTO();

            AssertionDTO assertionDTO = assertHandler.runAssertTry(apiRequestDTO.getAssertionDTO(), apiResponseDTO, nodeId);
            conversationDTO.setApiRequestDTO(apiRequestDTO);
            conversationDTO.setApiResponseDTO(apiResponseDTO);
            apiRequestDTO.setAssertionDTO(assertionDTO);

            return conversationDTO;

        } else {
            //conversation exist in db
            Conversation conversation = apiRequestDTO.getConversationId() != null || apiRequestDTO.getConversationId().isEmpty() ? conversationRepository.findOne(apiRequestDTO.getConversationId()) : null;
            BaseNode baseNode = conversation != null ? nodeRepository.findOne(conversation.getNodeId()) : null;
            nodeId = baseNode.getId();
            assertHandler.runAssert(apiRequestDTO.getAssertionDTO(), apiResponseDTO, nodeId);
            ApiRequest apiRequest = apiRequestRepository.findOne(apiRequestDTO.getId());
            if (apiRequest != null) {
                apiRequest = DTOToEntity.toEntity(apiRequestDTO);
                apiRequestRepository.save(apiRequest);
            }

            ConversationDTO conversationDTO = EntityToDTO.toDTO(conversationRepository.findOne(conversation.getId()));

            return conversationDTO;
        }
    }

    @RequestMapping(value = "/processor/projects/{id}", method = RequestMethod.GET)
    public List<NodeStatusResponseDTO> runProjectById(@PathVariable("id") String id, @RequestParam(value = "envId", required = false) String envId) throws URISyntaxException {
        logger.debug("Running all requests inside project : " + id);

        List<BaseNode> listOfNodes = nodeRepository.findNodesFromAProject(id);
        List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes, envId);
        return nodeStatuses;
    }

    @RequestMapping(value = "/processor/folders/{id}", method = RequestMethod.GET)
    public List<NodeStatusResponseDTO> runFolderById(@PathVariable("id") String id, @RequestParam(value = "envId", required = false) String envId) throws URISyntaxException {
        logger.debug("Running all requests inside folder : " + id);

        List<BaseNode> listOfNodes = nodeRepository.getChildren(id);
        List<NodeStatusResponseDTO> nodeStatuses = runNodes(listOfNodes, envId);
        return nodeStatuses;
    }

    private List<NodeStatusResponseDTO> runNodes(List<BaseNode> listOfNodes, String envId) throws URISyntaxException {
        String regex = "\\{\\{([^\\}\\}]*)\\}\\}";
        Environment env = null;
        if (envId != null && !envId.isEmpty()) {
            env = environmentController.findById(envId);
        }

        List<NodeStatusResponseDTO> nodeStatuses = new ArrayList<NodeStatusResponseDTO>();
        NodeStatusResponseDTO nodeStatus = null;

        for (BaseNode baseNode : listOfNodes) {
            String nodeType = baseNode.getNodeType();
            if (nodeType != null && (NodeType.PROJECT.name().equals(nodeType) || NodeType.FOLDER.name().equals(nodeType))) {
                continue;
            } else {
                Conversation conversation = baseNode.getConversation();
                if (conversation != null && conversation.getApiRequest() != null) {
                    ApiRequest apiRequest = conversation.getApiRequest();
                    String methodType = apiRequest.getMethodType();
                    String apiUrl = apiRequest.getApiUrl();
                    if (methodType != null && !methodType.isEmpty() && apiUrl != null && !apiUrl.isEmpty()) {
                        if (env != null) {
                            Pattern p = Pattern.compile(regex);
                            Matcher m = p.matcher(apiUrl);
                            String tempUrl = null;
                            while (m.find()) {
                                String exprVar = m.group(1);
                                String propVal = env.getPropertyValueByName(exprVar);
                                tempUrl = apiUrl.replaceFirst(regex, propVal);
                                apiUrl = tempUrl;
                            }
                        }
                        ApiRequestDTO apiRequestDTO = new ApiRequestDTO();
                        apiRequestDTO.setApiUrl(apiRequest.getApiUrl());
                        apiRequestDTO.setMethodType(methodType);
                        apiRequestDTO.setParameters(apiRequest.getParameters());
                        apiRequestDTO.setAssertionDTO(EntityToDTO.toDTO(apiRequest.getAssertion()));

                        ConversationDTO conversationDTO = requestProcessor(apiRequestDTO);

                        ApiResponseDTO apiResponseDTO = conversationDTO.getApiResponseDTO();
                        logger.debug(baseNode.getName() + " ran with status : " + apiResponseDTO.getStatus());

                        nodeStatus = new NodeStatusResponseDTO();
                        nodeStatus.setId(baseNode.getId());
                        nodeStatus.setName(baseNode.getName());
                        nodeStatus.setDescription(baseNode.getDescription());
                        nodeStatus.setApiUrl(apiUrl);
                        nodeStatus.setMethodType(methodType);
                        nodeStatus.setStatusCode(apiResponseDTO.getStatus());
                        nodeStatus.setDuration(conversationDTO.getDuration());


                        int successCount = 0;
                        int failureCount = 0;
                        // TODO: get AssertionDTO from ApiResponseDTO. Get bodyAssertsDTOs and loop through the list to count no. of success
                        List<BodyAssertDTO> bodyAssertDTOs = conversationDTO.getApiRequestDTO().getAssertionDTO().getBodyAssertDTOs();
                        if (bodyAssertDTOs != null) {
                            for (BodyAssertDTO bodyAssertDTO : bodyAssertDTOs) {
                                if (bodyAssertDTO.isSuccess()) {
                                    successCount++;
                                } else {
                                    failureCount++;
                                }
                            }
                        }
                        nodeStatus.setSuccessAsserts(successCount);
                        nodeStatus.setFailureAsserts(failureCount);

                        nodeStatuses.add(nodeStatus);

                        // TODO : Create ProjectRunnerLog and store nodeId as well as loggedConversationId.
                    }
                }
            }
        }
        return nodeStatuses;
    }

//    @RequestMapping(value = "/oauth/form", method = RequestMethod.POST)
//    public ModelAndView oauthFormRedirect(@ModelAttribute OAuth2DTO oAuth2RequestDTO) throws URISyntaxException {
//        List<String> scopes = oAuth2RequestDTO.getScopes();
//        String authorizationUrl = oAuth2RequestDTO.getAuthorizationUrl();
//        if (authorizationUrl == null || authorizationUrl.isEmpty()) {
//            return null;
//        }
//        URIBuilder uriBuilder = new URIBuilder(authorizationUrl);
//        List<NameValuePair> queryParams = uriBuilder.getQueryParams();
//        List<String> responseTypes = new ArrayList<String>();
//        if (queryParams != null && !queryParams.isEmpty()) {
//            for (NameValuePair nameValuePair : queryParams) {
//                if ("response_type".equals(nameValuePair.getName())) {
//                    responseTypes.add(nameValuePair.getValue());
//                    break;
//                }
//            }
//        }
//
//        BrowserClientRequestUrl browserClientRequestUrl = new BrowserClientRequestUrl(authorizationUrl, oAuth2RequestDTO.getClientId());
//        if (!responseTypes.isEmpty()) {
//            browserClientRequestUrl = browserClientRequestUrl.setResponseTypes(responseTypes);
//        }
//        String url = browserClientRequestUrl.setState("com/king/yyl/restfiddle").setScopes(scopes).setRedirectUri("http://localhost:8080/oauth/response").build();
//
//        return new ModelAndView("redirect:" + url);
//    }

//    @RequestMapping(value = "/oauth/demo-redirect", method = RequestMethod.GET)
//    public ModelAndView oauthRedirect() {
//        List<String> scopes = new ArrayList<String>();
//        scopes.add("https://www.googleapis.com/auth/userinfo.profile");
//        String url = new BrowserClientRequestUrl("https://accounts.google.com/o/oauth2/auth",
//            "82089573969-nocs1ulh96n5kfut1bh5cq7n1enlfoe8.apps.googleusercontent.com").setState("com/king/yyl/restfiddle").setScopes(scopes)
//            .setRedirectUri("http://localhost:8080/oauth/response").build();
//
//        return new ModelAndView("redirect:" + url);
//    }

}
