//
//package com.king.yyl.domain.data;
//
//import com.mongodb.BasicDBObject;
//import com.mongodb.DBCollection;
//import com.king.yyl.domain.*;
//import com.king.yyl.repository.HttpRequestHeaderRepository;
//import com.king.yyl.service.dto.*;
//import com.king.yyl.service.enums.NodeType;
//import com.king.yyl.service.enums.PermissionType;
//import com.king.yyl.web.rest.*;
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class SampleDataGenerator {
//
//    //    @Value("${application.host-uri}")
//    @Value("http://localhost:${server.port}")
//    private String hostUri;
//
//    @Autowired
//    private ConfigResource configResource;
//
////    @Autowired
////    private RoleResource roleResource;
//
//    @Autowired
//    private PermissionResource permissionResource;
//
////    @Autowired
////    private UserResource userResource;
//
//    @Autowired
//    private WorkspaceResource workspaceResource;
//
//    @Autowired
//    private ProjectResource projectResource;
//
//    @Autowired
//    private NodeResource nodeResource;
//
//    @Autowired
//    private ConversationResource conversationResource;
//
//    @Autowired
//    private TagResource tagResource;
//
////    @Autowired
////    private UserRepository userRepository;
//
//    @Autowired
//    private HttpRequestHeaderRepository requestHeaderRepository;
//
//    @Autowired
//    private OAuth2DataGenerator oauth2DataGenerator;
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    private String demoWorkspaceId;
//    private String socialWorkspaceId;
//
//    private String firstProjectId;
//    private String firstProjectRefId;
//
//    @SuppressWarnings("unused")
//    private String httpbinProjectId;
//    @SuppressWarnings("unused")
//    private String httpbinProjectRefId;
//    @SuppressWarnings("unused")
//    private String gitProjectId;
//    @SuppressWarnings("unused")
//    private String gitProjectRefId;
//
//    private String impTagId;
//    private String wlTagId;
//    private String sampleTagId;
//
//    ArrayList<TagDTO> tags;
//
//    @PostConstruct
//    public void initialize() throws JSONException {
//        if (isSampleDataPresent()) {
//            return;
//        }
//
//        addIndexEntityAuth();
//
//        loadWorkspaceData();
//
//        loadProjectData();
//
//        loadTagData();
//
//        loadNodeData();
//
//        loadHttpRequestHeaders();
//
//        oauth2DataGenerator.loadAsanaAPI();
//        oauth2DataGenerator.loadTrelloAPI();
//        oauth2DataGenerator.loadGitHubAPI();
//        oauth2DataGenerator.loadGoogleAPI();
//        oauth2DataGenerator.loadFacebookAPI();
//    }
//
//    private void loadPermissionData() {
//        PermissionDTO viewWorkspacePermission = new PermissionDTO();
//        viewWorkspacePermission.setType(PermissionType.VIEW_WORKSPACE.name());
//        permissionResource.create(viewWorkspacePermission);
//
//        PermissionDTO modifyWorkspacePermission = new PermissionDTO();
//        modifyWorkspacePermission.setType(PermissionType.MODIFY_WORKSPACE.name());
//        permissionResource.create(modifyWorkspacePermission);
//
//        PermissionDTO createWorkspacePermission = new PermissionDTO();
//        createWorkspacePermission.setType(PermissionType.CREATE_WORKSPACE.name());
//        permissionResource.create(createWorkspacePermission);
//
//        PermissionDTO deleteWorkspacePermission = new PermissionDTO();
//        deleteWorkspacePermission.setType(PermissionType.DELETE_WORKSPACE.name());
//        permissionResource.create(deleteWorkspacePermission);
//
//        PermissionDTO viewProject = new PermissionDTO();
//        viewProject.setType(PermissionType.VIEW_PROJECT.name());
//        permissionResource.create(viewProject);
//
//        PermissionDTO modifyProjectPermission = new PermissionDTO();
//        modifyProjectPermission.setType(PermissionType.MODIFY_PROJECT.name());
//        permissionResource.create(modifyProjectPermission);
//
//        PermissionDTO createProjectPermission = new PermissionDTO();
//        createProjectPermission.setType(PermissionType.CREATE_PROJECT.name());
//        permissionResource.create(createProjectPermission);
//
//        PermissionDTO deleteProjectPermission = new PermissionDTO();
//        deleteProjectPermission.setType(PermissionType.DELETE_PROJECT.name());
//        permissionResource.create(deleteProjectPermission);
//    }
//
////    private void createSuperUser() {
////        User user = userRepository.findByEmail("rf@example.com");
////        if (CommonUtil.isNotNull(user)) {
////            return;
////        }
////
////        PasswordDTO user1 = new PasswordDTO();
////        user1.setName("RF Admin");
////        user1.setEmail("rf@example.com");
////        user1.setPassword("rf");
////        userResource.create(user1);
////
////        PasswordDTO user2 = new PasswordDTO();
////        user2.setName("Anuja Kumar");
////        user2.setEmail("anuja@example.com");
////        user2.setPassword("rf");
////        userResource.create(user2);
////
////    }
//
//    private void loadUserData() {
//
//    }
//
//    private boolean isSampleDataPresent() {
//        // TODO : find sampleDataConfig by key
//        List<Config> configList = configResource.findAll();
//
//        if (configList == null || configList.size() == 0) {
//            ConfigDTO configDTO = new ConfigDTO();
//            configDTO.setName("Sample Data Present");
//            configDTO.setConfigKey("SAMPLE_DATA_PRESENT");
//            configResource.create(configDTO);
//            return false;
//        }
//        return true;
//    }
//
//    private void loadWorkspaceData() {
//        WorkspaceDTO demoWorkspace = new WorkspaceDTO();
//        demoWorkspace.setName("Demo Workspace");
//        demoWorkspace.setDescription("This is a demo workspace");
//        Workspace workspace1 = workspaceResource.create(demoWorkspace);
//        demoWorkspaceId = workspace1.getId();
//
//        WorkspaceDTO socialWorkspace = new WorkspaceDTO();
//        socialWorkspace.setName("Social Workspace");
//        socialWorkspace.setDescription("This is my social workspace");
//        Workspace workspace2 = workspaceResource.create(socialWorkspace);
//        socialWorkspaceId = workspace2.getId();
//    }
//
//    private void loadProjectData() throws JSONException {
//        ProjectDTO firstProject = new ProjectDTO();
//        firstProject.setName("My First Project");
//        Project proj1 = projectResource.create(demoWorkspaceId, firstProject);
//        firstProjectId = proj1.getId();
//        firstProjectRefId = proj1.getProjectRef().getId();
//
//        ProjectDTO secondProject = new ProjectDTO();
//        secondProject.setName("My Second Project");
//        projectResource.create(demoWorkspaceId, secondProject);
//
//        ProjectDTO httpbinProject = new ProjectDTO();
//        httpbinProject.setName("httpbin Project");
//        Project projHttpbin = projectResource.create(demoWorkspaceId, httpbinProject);
//        httpbinProjectId = projHttpbin.getId();
//        httpbinProjectRefId = projHttpbin.getProjectRef().getId();
//
//        ProjectDTO gitProject = new ProjectDTO();
//        gitProject.setName("Git Project");
//        Project projGit = projectResource.create(demoWorkspaceId, gitProject);
//        gitProjectId = projGit.getId();
//        gitProjectRefId = projGit.getProjectRef().getId();
//
//        ProjectDTO googleProject = new ProjectDTO();
//        googleProject.setName("Google");
//        Project projGoogle = projectResource.create(socialWorkspaceId, googleProject);
//        createSocialSample(projGoogle, "https://www.googleapis.com/plus/v1/people/me", "GET", "Google Plus", "Get Access Token from Auth and run this request. Enable Google Plus API in your account https://console.developers.google.com.", null, null);
//
//        ProjectDTO facebookProject = new ProjectDTO();
//        facebookProject.setName("Facebook");
//        Project projFacebook = projectResource.create(socialWorkspaceId, facebookProject);
//        createSocialSample(projFacebook, "https://graph.facebook.com/me", "GET", "Graph API", "Get Access Token from Auth and run this request. Do needful settings for your app in https://developers.facebook.com", null, null);
//
//        ProjectDTO twitterProject = new ProjectDTO();
//        twitterProject.setName("Twitter");
//        projectResource.create(socialWorkspaceId, twitterProject);
//
//        ProjectDTO linkedinProject = new ProjectDTO();
//        linkedinProject.setName("LinkedIn");
//        projectResource.create(socialWorkspaceId, linkedinProject);
//    }
//
//    private void loadNodeData() throws JSONException {
//        TagDTO tag1 = new TagDTO();
//        tag1.setId(impTagId);
//        TagDTO tag2 = new TagDTO();
//        tag2.setId(wlTagId);
//        TagDTO tag3 = new TagDTO();
//        tag3.setId(sampleTagId);
//        tags = new ArrayList<TagDTO>();
//        tags.add(tag1);
//        tags.add(tag2);
//        tags.add(tag3);
//
//        NodeDTO firstFolderNode = new NodeDTO();
//        firstFolderNode.setName("First Folder Node");
//        firstFolderNode.setNodeType(NodeType.FOLDER.name());
//        firstFolderNode.setProjectId(firstProjectId);
//
//        ConversationDTO conversationDTO = new ConversationDTO();
//        conversationDTO.setWorkspaceId(demoWorkspaceId);
//
//        RfRequestDTO rfRequestDTO = new RfRequestDTO();
//        rfRequestDTO.setApiUrl(hostUri + "/api/workspaces");
//        rfRequestDTO.setMethodType("GET");
//        conversationDTO.setRfRequestDTO(rfRequestDTO);
//
//        ConversationDTO postConversationDTO = new ConversationDTO();
//        postConversationDTO.setWorkspaceId(demoWorkspaceId);
//
//        RfRequestDTO rfRequestDTO2 = new RfRequestDTO();
//        rfRequestDTO2.setApiUrl(hostUri + "/api/workspaces");
//        rfRequestDTO2.setMethodType("POST");
//
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", "Test Workspace");
//        jsonObject.put("description", "This is test workspace from sample data generator");
//
//        rfRequestDTO2.setApiBody(jsonObject.toString(4));
//        postConversationDTO.setRfRequestDTO(rfRequestDTO2);
//
//        ConversationDTO createdConversation = conversationResource.create(conversationDTO);
//        ConversationDTO createdPostConversation = conversationResource.create(postConversationDTO);
//
//        // firstFolderNode.setConversationDTO(conversationDTO);
//
//        NodeDTO createdFolderNode = nodeResource.create(firstProjectRefId, firstFolderNode);
//
//        NodeDTO childNode = new NodeDTO();
//        childNode.setName("GET Workspace");
//        childNode.setDescription("A workspace is a collection of projects. This API returns list of available workspaces.");
//        childNode.setProjectId(firstProjectId);
//        childNode.setConversationDTO(createdConversation);
//        NodeDTO createdChildNode = nodeResource.create(createdFolderNode.getId(), childNode);
//        nodeResource.addTags(createdChildNode.getId(), tags);
//        createdConversation.setNodeDTO(createdChildNode);
//        conversationResource.update(createdConversation.getId(), createdConversation);
//
//        NodeDTO secondNode = new NodeDTO();
//        secondNode.setName("POST Workspace");
//        secondNode.setDescription("A workspace is a collection of projects. This API is used to create a new workspace.");
//        secondNode.setProjectId(firstProjectId);
//        secondNode.setConversationDTO(createdPostConversation);
//        NodeDTO createdSecondNode = nodeResource.create(firstProjectRefId, secondNode);
//        nodeResource.addTags(createdSecondNode.getId(), tags);
//        createdPostConversation.setNodeDTO(createdSecondNode);
//        conversationResource.update(createdPostConversation.getId(), createdPostConversation);
//
//        NodeDTO dummyNode = new NodeDTO();
//        dummyNode.setName("Dummy Node");
//        dummyNode.setProjectId(firstProjectId);
//        nodeResource.create(firstProjectRefId, dummyNode);
//
//        NodeDTO testNode = new NodeDTO();
//        testNode.setName("Test Node");
//        testNode.setProjectId(firstProjectId);
//        nodeResource.create(firstProjectRefId, testNode);
//
//        conversationDTO = new ConversationDTO();
//        conversationDTO.setWorkspaceId(demoWorkspaceId);
//
//        rfRequestDTO = new RfRequestDTO();
//        rfRequestDTO.setApiUrl(hostUri + "/api/workspaces/" + demoWorkspaceId + "/nodes/starred");
//        rfRequestDTO.setMethodType("GET");
//        conversationDTO.setRfRequestDTO(rfRequestDTO);
//        createdConversation = conversationResource.create(conversationDTO);
//        conversationDTO.setId(createdConversation.getId());
//
//        NodeDTO starredNode = new NodeDTO();
//        starredNode.setName("Starred Node");
//        starredNode.setDescription("This API returns a list of requests which are marked as star.");
//        starredNode.setStarred(Boolean.TRUE);
//        starredNode.setProjectId(firstProjectId);
//        starredNode.setConversationDTO(conversationDTO);
//        NodeDTO createdStarredNode = nodeResource.create(firstProjectRefId, starredNode);
//        createdConversation.setNodeDTO(createdStarredNode);
//        conversationResource.update(createdConversation.getId(), createdConversation);
//
//        createSampleRequest("httpbinProject", "http://httpbin.org/ip", "GET", "httpbin ip", "Returns Origin IP.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/user-agent", "GET", "httpbin User Agent", "Returns user-agent.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/headers", "GET", "httpbin Headers", "Returns header dict.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/get", "GET", "httpbin Get", "Returns GET data.", null, null, null);
//        UrlParamDTO urlParamDTO = new UrlParamDTO();
//        urlParamDTO.setKey("key1");
//        urlParamDTO.setValue("value1");
//        List<UrlParamDTO> urlParams = new ArrayList<UrlParamDTO>();
//        urlParams.add(urlParamDTO);
//        JSONObject jsonObjectSample = new JSONObject();
//        jsonObjectSample.put("name", "httpbin Post");
//        jsonObjectSample.put("description", "Test request using sample data generator");
//        createSampleRequest("httpbinProject", "http://httpbin.org/post", "POST", "Post", "POST method testing.", urlParams, jsonObjectSample, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/encoding/utf8", "GET", "UTF-8", "Returns page containing UTF-8 data.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/delete", "DELETE", "Delete", "Returns DELETE data.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/gzip", "GET", "Gzip", "Returns gzip-encoded data.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/deflate", "GET", "Deflate", "Returns deflate-encoded data.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/status/418", "GET", "Status code", "Returns given HTTP Status code.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/response-headers?Content-Type=text/plain;charset=UTF-8&Server=httpbin", "GET", "httpbin Content type", "Returns given response headers.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/redirect/6", "GET", "httpbin redirect", "Redirects.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/redirect-to/url=http://example.com/", "GET", "Redirect-to", "Redirects to the url.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/relative-redirect/6", "GET", "Relative redirect", "Relative Redirect.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/absolute-redirect/6", "GET", "Absolute redirect", "Absolute Redirect.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/cookies", "GET", "Cookies", "Returns cookie data.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/cookies/set?k2=v2&k1=v1", "GET", "Simple Cookies", "Sets one or more simple cookies.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/cookies/delete?k2=&k1=", "GET", "Delete Cookies", "Deletes one or more simple cookies.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/basic-auth/user/passwd", "GET", "Basic Auth", "Challenges HTTPBasic Auth.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/hidden-basic-auth/user/passwd", "GET", "Hidden Basic Auth", "404'd BasicAuth.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/digest-basic-auth/auth/user/passwd", "GET", "Digest Auth", "Challenges HTTP Digest Auth.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/stream/20", "GET", "Digest Auth", "Streams n–100 lines.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/delay/3", "GET", "Delay", "Delays responding for n–10 seconds.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/drip?duration=5&numbytes=5&code=200", "GET", "Drip", "Drips data over a duration after an optional initial delay, then (optionally) returns with the given status code.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/range/1024", "GET", "Range", "Streams n bytes, and allows specifying a Range header to select a subset of the data. Accepts a chunk_size and request duration parameter.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/html", "GET", "HTML", "Renders an HTML Page.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/robots.txt", "GET", "Robots.txt", "Returns some robots.txt rules.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/deny", "GET", "Deny", "Denied by robots.txt file.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/cache", "GET", "Cache", "Returns 200 unless an If-Modified-Since or If-None-Match header is provided, when it returns a 304.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/cache/60", "GET", "Cache-Control", "Sets a Cache-Control header for n seconds.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/bytes/1024", "GET", "Bytes", "Generates n random bytes of binary data, accepts optional seed integer parameter.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/stream-bytes/1024", "GET", "Stream-bytes", "Streams n random bytes of binary data, accepts optional seed and chunk_size integer parameters.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/links/10", "GET", "Bytes", "Returns page containing n HTML links.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/image", "GET", "Image", "Returns page containing an image.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/image/png", "GET", "Image PNG", "Returns page containing PNG image.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/image/jpeg", "GET", "Image JPEG", "Returns page containing JPEG image.", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/image/webp", "GET", "Image", "Returns page containing WEBP image.", null, null, null);
//        //CheckPost form returns the form but doesn't return response on submit.
//        createSampleRequest("httpbinProject", "http://httpbin.org/forms/post", "GET", "Post form", "HTML form that submits to /post", null, null, null);
//        createSampleRequest("httpbinProject", "http://httpbin.org/xml", "GET", "XML", "Returns some XML", null, null, null);
//
//        createSampleRequest("gitProject", "https://api.github.com/users/anujak", "GET", "Git Profile", "Get Git profile by username", null, null, null);
//        BasicAuthDTO basicAuth = new BasicAuthDTO();
//        basicAuth.setUsername("username");
//        basicAuth.setPassword("password");
//        createSampleRequest("gitProject", "https://api.github.com/user", "GET", "Basic Auth Logged in User Profile", "Get Git profile of logged in user by basic authentication", null, null, basicAuth);
//    }
//
//    private void createSampleRequest(String projectName, String apiUrl, String methodType, String name, String description, List<UrlParamDTO> urlParams, JSONObject jsonObject, BasicAuthDTO basicAuth) throws JSONException {
//        String projId = null;
//        String projRefId = null;
//        try {
//            Field declaredField = this.getClass().getDeclaredField(projectName + "Id");
//            projId = (String) declaredField.get(this);
//            Field declaredField2 = this.getClass().getDeclaredField(projectName + "RefId");
//            projRefId = (String) declaredField2.get(this);
//        } catch (NoSuchFieldException | SecurityException e) {
//            e.printStackTrace();
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        ConversationDTO conversationDTO = new ConversationDTO();
//        conversationDTO.setWorkspaceId(demoWorkspaceId);
//
//        RfRequestDTO requestDTO = new RfRequestDTO();
//        requestDTO.setApiUrl(apiUrl);
//        requestDTO.setMethodType(methodType);
//
//        conversationDTO.setRfRequestDTO(requestDTO);
//        if (urlParams != null) {
//            requestDTO.setUrlParams(urlParams);
//        }
//        if (jsonObject != null) {
//            requestDTO.setApiBody(jsonObject.toString(4));
//        }
//        if (basicAuth != null) {
//            requestDTO.setBasicAuthDTO(basicAuth);
//        }
//        ConversationDTO conversation = conversationResource.create(conversationDTO);
//        NodeDTO node = new NodeDTO();
//        node.setName(name);
//        node.setDescription(description);
//        node.setProjectId(projId);
//        node.setConversationDTO(conversation);
//        NodeDTO createdHttpbinNode = nodeResource.create(projRefId, node);
//        //nodeResource.addTags(httpbinNode.getId(), tags);
//        conversation.setNodeDTO(createdHttpbinNode);
//        conversationResource.update(conversation.getId(), conversation);
//    }
//
//    private void createSocialSample(Project project, String apiUrl, String methodType, String name, String description, List<UrlParamDTO> urlParams, JSONObject jsonObject) throws JSONException {
//        String projectId = project.getId();
//        String projectRefId = project.getProjectRef().getId();
//
//        ConversationDTO socialDTO = new ConversationDTO();
//        socialDTO.setWorkspaceId(socialWorkspaceId);
//
//        RfRequestDTO socialReqDTO = new RfRequestDTO();
//        socialReqDTO.setApiUrl(apiUrl);
//        socialReqDTO.setMethodType(methodType);
//        socialDTO.setRfRequestDTO(socialReqDTO);
//        if (urlParams != null) {
//            socialReqDTO.setUrlParams(urlParams);
//        }
//        if (jsonObject != null) {
//            socialReqDTO.setApiBody(jsonObject.toString(4));
//        }
//        ConversationDTO conversationsocial = conversationResource.create(socialDTO);
//        NodeDTO socialNode = new NodeDTO();
//        socialNode.setName(name);
//        socialNode.setDescription(description);
//        socialNode.setProjectId(projectId);
//        socialNode.setConversationDTO(conversationsocial);
//        NodeDTO createdSocialNode = nodeResource.create(projectRefId, socialNode);
//        //nodeResource.addTags(httpbinNode.getId(), tags);
//        conversationsocial.setNodeDTO(createdSocialNode);
//        conversationResource.update(conversationsocial.getId(), conversationsocial);
//    }
//
//    private void loadTagData() {
//        TagDTO tagDTO = new TagDTO();
//        tagDTO.setName("Important");
//        Tag impTag = tagResource.create(demoWorkspaceId, tagDTO);
//        impTagId = impTag.getId();
//
//        TagDTO secondTag = new TagDTO();
//        secondTag.setName("Wishlist");
//        Tag wlTag = tagResource.create(socialWorkspaceId, secondTag);
//        wlTagId = wlTag.getId();
//
//        TagDTO tagDTO2 = new TagDTO();
//        tagDTO2.setName("Sample");
//        Tag sampleTag = tagResource.create(demoWorkspaceId, tagDTO2);
//        sampleTagId = sampleTag.getId();
//    }
//
//    private void loadHttpRequestHeaders() {
//        String[] headersArr = {"Accept", "Accept-Charset", "Accept-Encoding", "Accept-Language", "Accept-Datetime", "Authorization",
//            "Cache-Control", "Connection", "Cookie", "Content-Length", "Content-MD5", "Content-Type", "Date", "Expect", "From", "Host",
//            "If-Match", "If-Modified-Since", "If-None-Match", "If-Range", "If-Unmodified-Since", "Max-Forwards", "Origin", "Pragma",
//            "Proxy-Authorization", "Range", "Referer", "TE", "User-Agent", "Upgrade", "Via", "Warning", "X-Requested-With", "DNT",
//            "X-Forwarded-For", "X-Forwarded-Host", "X-Forwarded-Proto", "Front-End-Https", "X-Http-Method-Override", "X-ATT-DeviceId",
//            "X-Wap-Profile", "Proxy-Connection"};
//
//        List<HttpRequestHeader> headers = new ArrayList<HttpRequestHeader>();
//        HttpRequestHeader header = null;
//
//        for (int i = 0; i < headersArr.length; i++) {
//            header = new HttpRequestHeader();
//            header.setName(headersArr[i]);
//            headers.add(header);
//        }
//
//        requestHeaderRepository.save(headers);
//    }
//
//    private void addIndexEntityAuth() {
//        DBCollection dbCollectionAuth = mongoTemplate.getCollection("EntityAuth");
//        dbCollectionAuth.createIndex(new BasicDBObject("expireAt", 1), new BasicDBObject("expireAfterSeconds", 0));
//    }
//}
