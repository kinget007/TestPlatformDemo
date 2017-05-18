package com.king.yyl.web.rest;

import com.king.yyl.domain.BaseNode;
import com.king.yyl.domain.GenericEntity;
import com.king.yyl.domain.GenericEntityField;
import com.king.yyl.domain.parameters.Parameter;
import com.king.yyl.domain.parameters.BodyParameter;
import com.king.yyl.repository.GenericEntityDataRepository;
import com.king.yyl.repository.GenericEntityRepository;
import com.king.yyl.repository.NodeRepository;
import com.king.yyl.service.dto.ApiRequestDTO;
import com.king.yyl.service.dto.ConversationDTO;
import com.king.yyl.service.dto.NodeDTO;
import com.king.yyl.service.dto.StatusResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class GenerateApiResource {
    Logger logger = LoggerFactory.getLogger(GenerateApiResource.class);

    @Value("http://localhost:${server.port}")
    private String hostUri;

    @Autowired
    private NodeResource nodeController;

    @Autowired
    private NodeRepository nodeRepository;

    @Autowired
    private ConversationResource conversationController;

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenericEntityDataRepository genericEntityDataRepository;

    @RequestMapping(value="/entities/{id}/generate-api", method = RequestMethod.POST)
    public
    @ResponseBody
    StatusResponse generateApiByEntityId(@PathVariable("id") String id) throws JSONException, MalformedURLException {
        logger.debug("Generating APIs for entity with id: " + id);

        GenericEntity entity = genericEntityRepository.findOne(id);

        String baseNodeId = entity.getBaseNodeId();
        BaseNode entityNode = nodeController.findById(baseNodeId);

        return generateApi(entityNode);
    }

    StatusResponse generateApiByEntity(GenericEntity entity) throws JSONException, MalformedURLException {

        String baseNodeId = entity.getBaseNodeId();
        BaseNode entityNode = nodeController.findById(baseNodeId);

        return generateApi(entityNode);
    }

    public StatusResponse generateApi(BaseNode entityNode) throws JSONException, MalformedURLException {
        GenericEntity genericEntity = entityNode.getGenericEntity();

        // API to GENERATE >> List of Entity Data
        ConversationDTO conversationDTO = new ConversationDTO();
        ApiRequestDTO apiRequestDTO = new ApiRequestDTO();

        String projectId = entityNode.getProjectId();
        apiRequestDTO.setApiUrl(hostUri);
        apiRequestDTO.setMethodType("GET");
        conversationDTO.setApiRequestDTO(apiRequestDTO);

        ConversationDTO createdConversation = conversationController.create(conversationDTO);
        conversationDTO.setId(createdConversation.getId());

        String nodeName = "Get List of " + entityNode.getName();
        NodeDTO createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

        // API to GENERATE >> Get Entity Data By Id
        conversationDTO = new ConversationDTO();
        apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName() + "/{uuid}");

        apiRequestDTO.setMethodType("GET");
        conversationDTO.setApiRequestDTO(apiRequestDTO);

        createdConversation = conversationController.create(conversationDTO);
        conversationDTO.setId(createdConversation.getId());

        nodeName = "Get " + entityNode.getName() + " by Id";
        createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

        // API to GENERATE >> Delete Entity Data By Id
        conversationDTO = new ConversationDTO();
        apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName() + "/{uuid}");
        apiRequestDTO.setMethodType("DELETE");
        conversationDTO.setApiRequestDTO(apiRequestDTO);

        createdConversation = conversationController.create(conversationDTO);
        conversationDTO.setId(createdConversation.getId());

        nodeName = "Delete " + entityNode.getName();
        createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

        // API to GENERATE >> Create Entity Data
        conversationDTO = new ConversationDTO();
        apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName());
        apiRequestDTO.setMethodType("POST");

        JSONObject jsonObject = getFieldJson(genericEntity);
        // Make a pretty-printed JSON text of this JSONObject.
        BodyParameter bodyParameter = new BodyParameter();
        bodyParameter.setValue(jsonObject.toString(4));
        List<Parameter> parameters = apiRequestDTO.getParameters();
        parameters.add(bodyParameter);
        apiRequestDTO.setParameters(parameters);
        conversationDTO.setApiRequestDTO(apiRequestDTO);

        createdConversation = conversationController.create(conversationDTO);
        conversationDTO.setId(createdConversation.getId());

        nodeName = "Create " + entityNode.getName();
        createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

        // API to GENERATE >> Update Entity Data
        conversationDTO = new ConversationDTO();
        apiRequestDTO = new ApiRequestDTO();
        apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName() + "/{uuid}");
        apiRequestDTO.setMethodType("PUT");

        jsonObject = getFieldJson(genericEntity);
        // id is required in case of update.
        jsonObject.put("_id", "{uuid}");


        apiRequestDTO.setParameters(parameters);
        conversationDTO.setApiRequestDTO(apiRequestDTO);




        apiRequestDTO.setParameters(parameters);
        conversationDTO.setApiRequestDTO(apiRequestDTO);

        createdConversation = conversationController.create(conversationDTO);
        conversationDTO.setId(createdConversation.getId());

        nodeName = "Update " + entityNode.getName();
        createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

        if (entityNode.getName().equals("User")) {
            // API to GENERATE >> Login Entity
            conversationDTO = new ConversationDTO();
            apiRequestDTO = new ApiRequestDTO();
            apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/login");
            apiRequestDTO.setMethodType("POST");

            JSONObject json = new JSONObject();
            json.put("username", "");
            json.put("password", "");

            BodyParameter bodyParameter2 = new BodyParameter();
            bodyParameter2.setValue(json.toString(4));
            List<Parameter> parameters2 = apiRequestDTO.getParameters();
            parameters2.add(bodyParameter2);

            apiRequestDTO.setParameters(parameters2);
            conversationDTO.setApiRequestDTO(apiRequestDTO);

            createdConversation = conversationController.create(conversationDTO);
            conversationDTO.setId(createdConversation.getId());

            nodeName = "Login " + entityNode.getName();
            createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);

            // API to GENERATE >> Get Entity Data By Id
            conversationDTO = new ConversationDTO();
            apiRequestDTO = new ApiRequestDTO();
            apiRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/logout?authToken=");
            apiRequestDTO.setMethodType("GET");
            conversationDTO.setApiRequestDTO(apiRequestDTO);

            createdConversation = conversationController.create(conversationDTO);
            conversationDTO.setId(createdConversation.getId());

            nodeName = "Logout " + entityNode.getName();
            createdNode = createNode(nodeName, entityNode.getId(), projectId, conversationDTO);
        }

        return null;
    }

    private NodeDTO createNode(String nodeName, String parentId, String projectId, ConversationDTO conversationDTO) throws JSONException, MalformedURLException {
        NodeDTO childNode = new NodeDTO();
        childNode.setName(nodeName);
        childNode.setProjectId(projectId);
        childNode.setConversationDTO(conversationDTO);
        NodeDTO createdNode = nodeController.create(parentId, childNode);
        return createdNode;
    }

    private JSONObject getFieldJson(GenericEntity genericEntity) throws JSONException {
        // Create JSON template for the entity data based on fields and set it as api body.
        List<GenericEntityField> fields = genericEntity.getFields();
        JSONObject jsonObject = new JSONObject();
        for (GenericEntityField genericEntityField : fields) {
            String type = genericEntityField.getType();
            if ("STRING".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), "");
            } else if ("NUMBER".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), Long.valueOf(1));
            } else if ("BOOLEAN".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), false);
            } else if ("DATE".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), new Date());
            } else if ("NUMBER".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), Long.valueOf(1));
            } else if ("OBJECT".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), new JSONObject());
            } else if ("ARRAY".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), new JSONArray());
            } else if ("Geographic point".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), new JSONObject("{\"lat\" : 18.5204303,\"lng\" : 73.8567437}"));
            } else if ("relation".equalsIgnoreCase(type)) {
                jsonObject.put(genericEntityField.getName(), new JSONObject("{\"_rel\":{\"entity\" : \"{Entity Name}\",\"_id\" : \"{Entity _id}\"}}"));
            }
        }
        return jsonObject;
    }
}
