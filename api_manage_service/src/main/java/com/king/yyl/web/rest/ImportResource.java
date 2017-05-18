package com.king.yyl.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.king.yyl.domain.BaseNode;
import com.king.yyl.repository.NodeRepository;
import com.king.yyl.web.rest.util.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.inject.Inject;
import java.util.List;

//import io.swagger.models.*;
//import io.swagger.models.parameters.Parameter;
//import io.swagger.parser.SwaggerParser;

@RestController
@RequestMapping("/api")
public class ImportResource {
    Logger logger = LoggerFactory.getLogger(ImportResource.class);

    @Autowired
    private NodeResource nodeController;

    @Autowired
    private ConversationResource conversationController;

    @Autowired
    private ProjectResource projectController;

    @Inject
    private NodeRepository nodeRepository;

    @RequestMapping(value="/import/com.king.yyl.restfiddle", method = RequestMethod.POST)
    public
    @ResponseBody
    void importRestFiddle(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                ObjectMapper mapper = new ObjectMapper();
                TreeNode projectNode = mapper.readValue(bytes, TreeNode.class);
                System.out.println("projectNode : " + projectNode);
                createNodeRecursively(projectNode, projectNode.getChildren());
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    private void createNodeRecursively(BaseNode parent, List<TreeNode> nodes) {
        BaseNode node = null;
        for (TreeNode treeNode : nodes) {
            node = new BaseNode();
            node.setName(treeNode.getName());
            node.setDescription(treeNode.getDescription());
            node.setNodeType(treeNode.getNodeType());
            node.setStarred(treeNode.getStarred());
            node.setPosition(treeNode.getPosition());
            node.setProjectId(treeNode.getProjectId());
            node.setParentId(parent.getId());// Important
            node.setWorkspaceId(parent.getWorkspaceId());
            node = nodeRepository.save(node);
            List<TreeNode> children = treeNode.getChildren();
            if (children != null && !children.isEmpty()) {
                createNodeRecursively(node, children);
            }
        }
    }

//    @RequestMapping(value="/import/swagger", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void importSwagger(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
//        try {
//            swaggerToRFConverter(projectId, name, file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

//    @RequestMapping(value="/import/raml", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void importRaml(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
//        try {
//            ramlToRFConverter(projectId, name, file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    @RequestMapping(value="/import/postman", method = RequestMethod.POST)
//    public
//    @ResponseBody
//    void importPostman(@RequestParam("projectId") String projectId, @RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
//
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                String fileContent = new String(bytes);
//                JSONObject pmCollection = new JSONObject(fileContent);
//
//                String folderName = pmCollection.getString("name");
//                System.out.println(folderName);
//                //
//                NodeDTO folderNode = createFolder(projectId, folderName);
//                ConversationDTO conversationDTO = null;
//
//                JSONArray requests = pmCollection.getJSONArray("requests");
//                int len = requests.length();
//                for (int i = 0; i < len; i++) {
//                    JSONObject request = (JSONObject) requests.get(i);
//                    String requestName = request.getString("name");
//                    String requestDescription = request.getString("description");
//                    String requestUrl = request.getString("url");
//                    String requestMethod = request.getString("method");
//
//                    conversationDTO = new ConversationDTO();
//
//                    // TODO : Set workspace Id to the conversation
//                    // conversationDTO.setWorkspaceId(project.getWorkspace().getId());
//
//                    ApiRequestDTO apiRequestDTO = new ApiRequestDTO();
//                    apiRequestDTO.setApiUrl(requestUrl);
//                    apiRequestDTO.setMethodType(requestMethod);
//
//                    List<com.king.yyl.domain.parameters.Parameter> parameters = new ArrayList<>();
//
//                    String headersString = request.getString("headers");
//                    if (headersString != null && !headersString.isEmpty()) {
//                        List<HeaderParameter> headerParameters = new ArrayList<HeaderParameter>();
//                        HeaderParameter headerParameter = null;
//                        String[] headersArr = headersString.split("\n");
//                        for (String header : headersArr) {
//                            String[] headerToken = header.split(":");
//                            String headerName = headerToken[0];
//                            String headerValue = headerToken[1].trim();
//
//                            headerParameter = new HeaderParameter();
//                            headerParameter.setName(headerName);
//                            headerParameter.setValue(headerValue);
//                            headerParameters.add(headerParameter);
//                        }
//                    }
//
//                    String dataMode = request.getString("dataMode");
//                    if ("raw".equals(dataMode)) {
//                        String rawModeData = request.getString("rawModeData");
//                        rfRequestDTO.setApiBody(rawModeData);
//                    } else if ("params".equals(dataMode)) {
//                        JSONArray formParamsArr = request.getJSONArray("data");
//                        int arrLen = formParamsArr.length();
//
//                        FormDataDTO formParam = null;
//                        List<FormDataDTO> formParams = new ArrayList<FormDataDTO>();
//                        for (int j = 0; j < arrLen; j++) {
//                            JSONObject formParamJSON = (JSONObject) formParamsArr.get(j);
//                            formParam = new FormDataDTO();
//                            formParam.setKey(formParamJSON.getString("key"));
//                            formParam.setValue(formParamJSON.getString("value"));
//                            formParams.add(formParam);
//                        }
//                        rfRequestDTO.setFormParams(formParams);
//                    }
//
//                    conversationDTO.setRfRequestDTO(rfRequestDTO);
//                    ConversationDTO createdConversation = conversationController.create(conversationDTO);
//                    conversationDTO.setId(createdConversation.getId());
//
//                    // Request Node
//                    NodeDTO childNode = new NodeDTO();
//                    childNode.setName(requestName);
//                    childNode.setDescription(requestDescription);
//                    childNode.setProjectId(projectId);
//                    childNode.setConversationDTO(conversationDTO);
//                    NodeDTO createdChildNode = nodeController.create(folderNode.getId(), childNode);
//                    System.out.println("created node : " + createdChildNode.getName());
//                }
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//            }
//        }
//    }
//
//    private void ramlToRFConverter(String projectId, String name, MultipartFile file) throws IOException {
//        // Raml raml = new RamlDocumentBuilder().build(ramlLocation);
//
//    }

//    private NodeDTO createFolder(String projectId, String folderName) throws JSONException {
//        Project project = projectController.findById(null, projectId);
//
//        NodeDTO folderNodeDTO = new NodeDTO();
//        folderNodeDTO.setName(folderName);
//        folderNodeDTO.setNodeType(NodeType.FOLDER.name());
//        folderNodeDTO.setProjectId(projectId);
//
//        NodeDTO collectionNode = nodeController.create(project.getProjectRef().getId(), folderNodeDTO);
//        return collectionNode;
//    }

//    // Swagger sample json : http://petstore.swagger.io/v2/swagger.json
//    private void swaggerToRFConverter(String projectId, String name, MultipartFile file) throws IOException, JSONException {
//        // MultipartFile file
//        File tempFile = File.createTempFile("RF_SWAGGER_IMPORT", "JSON");
//        file.transferTo(tempFile);
//        Swagger swagger = new SwaggerParser().read(tempFile.getAbsolutePath());
//
//        String host = swagger.getHost();
//        String basePath = swagger.getBasePath();
//
//        Info info = swagger.getInfo();
//        String title = info.getTitle();
//        String description = info.getDescription();
//
//        NodeDTO folderNode = createFolder(projectId, title);
//        folderNode.setDescription(description);
//
//        ConversationDTO conversationDTO = null;
//
//        Map<String, Path> paths = swagger.getPaths();
//        Set<String> keySet = paths.keySet();
//        for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext(); ) {
//            String pathKey = iterator.next();
//            Path path = paths.get(pathKey);
//
//            Map<HttpMethod, Operation> operationMap = path.getOperationMap();
//            Set<HttpMethod> operationsKeySet = operationMap.keySet();
//            for (Iterator<HttpMethod> operIterator = operationsKeySet.iterator(); operIterator.hasNext(); ) {
//                HttpMethod httpMethod = operIterator.next();
//                Operation operation = operationMap.get(httpMethod);
//
//                conversationDTO = new ConversationDTO();
//                RfRequestDTO rfRequestDTO = new RfRequestDTO();
//                rfRequestDTO.setApiUrl("http://" + host + basePath + pathKey);
//                rfRequestDTO.setMethodType(httpMethod.name());
//
//                List<Parameter> parameters = operation.getParameters();
//                for (Parameter parameter : parameters) {
//                    String parameterName = parameter.getName();
//                    String parameterDescription = parameter.getDescription();
//                    String parameterIn = parameter.getIn();
//                }
//                conversationDTO.setRfRequestDTO(rfRequestDTO);
//                ConversationDTO createdConversation = conversationController.create(conversationDTO);
//                conversationDTO.setId(createdConversation.getId());
//
//                String operationId = operation.getOperationId();
//                String summary = operation.getSummary();
//                // Request Node
//                NodeDTO childNode = new NodeDTO();
//                childNode.setName(operationId);
//                childNode.setDescription(summary);
//                childNode.setProjectId(projectId);
//                childNode.setConversationDTO(conversationDTO);
//                NodeDTO createdChildNode = nodeController.create(folderNode.getId(), childNode);
//                System.out.println("created node : " + createdChildNode.getName());
//            }
//        }
//    }
}
