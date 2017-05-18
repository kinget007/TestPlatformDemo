//package com.king.yyl.web.rest;
//
//import com.king.yyl.ApimanageserviceApp;
//
//import com.king.yyl.config.SecurityBeanOverrideConfiguration;
//
//import com.king.yyl.domain.ApiInfo;
//import com.king.yyl.repository.ApiInfoRepository;
//import com.king.yyl.service.ApiInfoService;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.util.ReflectionTestUtils;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import javax.inject.Inject;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the ApiInfoResource REST controller.
// *
// * @see ApiInfoResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ApimanageserviceApp.class, SecurityBeanOverrideConfiguration.class})
//public class ApiInfoResourceIntTest {
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SWAGGER_URI = "AAAAAAAAAA";
//    private static final String UPDATED_SWAGGER_URI = "BBBBBBBBBB";
//
//    private static final String DEFAULT_VERSION = "AAAAAAAAAA";
//    private static final String UPDATED_VERSION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SCHEME = "AAAAAAAAAA";
//    private static final String UPDATED_SCHEME = "BBBBBBBBBB";
//
//    private static final String DEFAULT_HOST = "AAAAAAAAAA";
//    private static final String UPDATED_HOST = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_PORT = 1;
//    private static final Integer UPDATED_PORT = 2;
//
//    private static final String DEFAULT_PATH = "AAAAAAAAAA";
//    private static final String UPDATED_PATH = "BBBBBBBBBB";
//
//    private static final String DEFAULT_OPERATION = "AAAAAAAAAA";
//    private static final String UPDATED_OPERATION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_COOKIE_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_COOKIE_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_FORM_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_FORM_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_QUERY_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_QUERY_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PATH_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_PATH_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_HEAD_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_HEAD_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_BODY_PARAMS = "AAAAAAAAAA";
//    private static final String UPDATED_BODY_PARAMS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_REQUEST_DESC = "AAAAAAAAAA";
//    private static final String UPDATED_REQUEST_DESC = "BBBBBBBBBB";
//
//    private static final String DEFAULT_RESPONSE_DESC = "AAAAAAAAAA";
//    private static final String UPDATED_RESPONSE_DESC = "BBBBBBBBBB";
//
//    private static final String DEFAULT_CHECKS = "AAAAAAAAAA";
//    private static final String UPDATED_CHECKS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PROXYS = "AAAAAAAAAA";
//    private static final String UPDATED_PROXYS = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PROJECT_ID = "AAAAAAAAAA";
//    private static final String UPDATED_PROJECT_ID = "BBBBBBBBBB";
//
//    private static final String DEFAULT_PROJECT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_PROJECT_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_ETC = "AAAAAAAAAA";
//    private static final String UPDATED_ETC = "BBBBBBBBBB";
//
//    @Inject
//    private ApiInfoRepository apiInfoRepository;
//
//    @Inject
//    private ApiInfoService apiInfoService;
//
//    @Inject
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Inject
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    private MockMvc restApiInfoMockMvc;
//
//    private ApiInfo apiInfo;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        ApiInfoResource apiInfoResource = new ApiInfoResource();
//        ReflectionTestUtils.setField(apiInfoResource, "apiInfoService", apiInfoService);
//        this.restApiInfoMockMvc = MockMvcBuilders.standaloneSetup(apiInfoResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static ApiInfo createEntity() {
//        ApiInfo apiInfo = new ApiInfo()
//                .description(DEFAULT_DESCRIPTION)
//                .swaggerUri(DEFAULT_SWAGGER_URI)
//                .version(DEFAULT_VERSION)
//                .scheme(DEFAULT_SCHEME)
//                .host(DEFAULT_HOST)
//                .port(DEFAULT_PORT)
//                .path(DEFAULT_PATH)
//                .operation(DEFAULT_OPERATION)
//                .cookieParams(DEFAULT_COOKIE_PARAMS)
//                .formParams(DEFAULT_FORM_PARAMS)
//                .queryParams(DEFAULT_QUERY_PARAMS)
//                .pathParams(DEFAULT_PATH_PARAMS)
//                .headParams(DEFAULT_HEAD_PARAMS)
//                .bodyParams(DEFAULT_BODY_PARAMS)
//                .requestDesc(DEFAULT_REQUEST_DESC)
//                .responseDesc(DEFAULT_RESPONSE_DESC)
//                .checks(DEFAULT_CHECKS)
//                .proxys(DEFAULT_PROXYS)
//                .projectId(DEFAULT_PROJECT_ID)
//                .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
//                .etc(DEFAULT_ETC);
//        return apiInfo;
//    }
//
//    @Before
//    public void initTest() {
//        apiInfoRepository.deleteAll();
//        apiInfo = createEntity();
//    }
//
//    @Test
//    public void createApiInfo() throws Exception {
//        int databaseSizeBeforeCreate = apiInfoRepository.findAll().size();
//
//        // Create the ApiInfo
//
//        restApiInfoMockMvc.perform(post("/api/api-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(apiInfo)))
//            .andExpect(status().isCreated());
//
//        // Validate the ApiInfo in the database
//        List<ApiInfo> apiInfoList = apiInfoRepository.findAll();
//        assertThat(apiInfoList).hasSize(databaseSizeBeforeCreate + 1);
//        ApiInfo testApiInfo = apiInfoList.get(apiInfoList.size() - 1);
//        assertThat(testApiInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testApiInfo.getSwaggerUri()).isEqualTo(DEFAULT_SWAGGER_URI);
//        assertThat(testApiInfo.getVersion()).isEqualTo(DEFAULT_VERSION);
//        assertThat(testApiInfo.getScheme()).isEqualTo(DEFAULT_SCHEME);
//        assertThat(testApiInfo.getHost()).isEqualTo(DEFAULT_HOST);
//        assertThat(testApiInfo.getPort()).isEqualTo(DEFAULT_PORT);
//        assertThat(testApiInfo.getPath()).isEqualTo(DEFAULT_PATH);
//        assertThat(testApiInfo.getOperation()).isEqualTo(DEFAULT_OPERATION);
//        assertThat(testApiInfo.getCookieParams()).isEqualTo(DEFAULT_COOKIE_PARAMS);
//        assertThat(testApiInfo.getFormParams()).isEqualTo(DEFAULT_FORM_PARAMS);
//        assertThat(testApiInfo.getQueryParams()).isEqualTo(DEFAULT_QUERY_PARAMS);
//        assertThat(testApiInfo.getPathParams()).isEqualTo(DEFAULT_PATH_PARAMS);
//        assertThat(testApiInfo.getHeadParams()).isEqualTo(DEFAULT_HEAD_PARAMS);
//        assertThat(testApiInfo.getBodyParams()).isEqualTo(DEFAULT_BODY_PARAMS);
//        assertThat(testApiInfo.getRequestDesc()).isEqualTo(DEFAULT_REQUEST_DESC);
//        assertThat(testApiInfo.getResponseDesc()).isEqualTo(DEFAULT_RESPONSE_DESC);
//        assertThat(testApiInfo.getChecks()).isEqualTo(DEFAULT_CHECKS);
//        assertThat(testApiInfo.getProxys()).isEqualTo(DEFAULT_PROXYS);
//        assertThat(testApiInfo.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
//        assertThat(testApiInfo.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
//        assertThat(testApiInfo.getEtc()).isEqualTo(DEFAULT_ETC);
//    }
//
//    @Test
//    public void createApiInfoWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = apiInfoRepository.findAll().size();
//
//        // Create the ApiInfo with an existing ID
//        ApiInfo existingApiInfo = new ApiInfo();
//        existingApiInfo.setId("existing_id");
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restApiInfoMockMvc.perform(post("/api/api-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(existingApiInfo)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<ApiInfo> apiInfoList = apiInfoRepository.findAll();
//        assertThat(apiInfoList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    public void getAllApiInfos() throws Exception {
//        // Initialize the database
//        apiInfoRepository.save(apiInfo);
//
//        // Get all the apiInfoList
//        restApiInfoMockMvc.perform(get("/api/api-infos?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(apiInfo.getId())))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].swaggerUri").value(hasItem(DEFAULT_SWAGGER_URI.toString())))
//            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION.toString())))
//            .andExpect(jsonPath("$.[*].scheme").value(hasItem(DEFAULT_SCHEME.toString())))
//            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
//            .andExpect(jsonPath("$.[*].port").value(hasItem(DEFAULT_PORT)))
//            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH.toString())))
//            .andExpect(jsonPath("$.[*].operation").value(hasItem(DEFAULT_OPERATION.toString())))
//            .andExpect(jsonPath("$.[*].cookieParams").value(hasItem(DEFAULT_COOKIE_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].formParams").value(hasItem(DEFAULT_FORM_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].queryParams").value(hasItem(DEFAULT_QUERY_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].pathParams").value(hasItem(DEFAULT_PATH_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].headParams").value(hasItem(DEFAULT_HEAD_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].bodyParams").value(hasItem(DEFAULT_BODY_PARAMS.toString())))
//            .andExpect(jsonPath("$.[*].requestDesc").value(hasItem(DEFAULT_REQUEST_DESC.toString())))
//            .andExpect(jsonPath("$.[*].responseDesc").value(hasItem(DEFAULT_RESPONSE_DESC.toString())))
//            .andExpect(jsonPath("$.[*].checks").value(hasItem(DEFAULT_CHECKS.toString())))
//            .andExpect(jsonPath("$.[*].proxys").value(hasItem(DEFAULT_PROXYS.toString())))
//            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
//            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].etc").value(hasItem(DEFAULT_ETC.toString())));
//    }
//
//    @Test
//    public void getApiInfo() throws Exception {
//        // Initialize the database
//        apiInfoRepository.save(apiInfo);
//
//        // Get the apiInfo
//        restApiInfoMockMvc.perform(get("/api/api-infos/{id}", apiInfo.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(apiInfo.getId()))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.swaggerUri").value(DEFAULT_SWAGGER_URI.toString()))
//            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION.toString()))
//            .andExpect(jsonPath("$.scheme").value(DEFAULT_SCHEME.toString()))
//            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
//            .andExpect(jsonPath("$.port").value(DEFAULT_PORT))
//            .andExpect(jsonPath("$.path").value(DEFAULT_PATH.toString()))
//            .andExpect(jsonPath("$.operation").value(DEFAULT_OPERATION.toString()))
//            .andExpect(jsonPath("$.cookieParams").value(DEFAULT_COOKIE_PARAMS.toString()))
//            .andExpect(jsonPath("$.formParams").value(DEFAULT_FORM_PARAMS.toString()))
//            .andExpect(jsonPath("$.queryParams").value(DEFAULT_QUERY_PARAMS.toString()))
//            .andExpect(jsonPath("$.pathParams").value(DEFAULT_PATH_PARAMS.toString()))
//            .andExpect(jsonPath("$.headParams").value(DEFAULT_HEAD_PARAMS.toString()))
//            .andExpect(jsonPath("$.bodyParams").value(DEFAULT_BODY_PARAMS.toString()))
//            .andExpect(jsonPath("$.requestDesc").value(DEFAULT_REQUEST_DESC.toString()))
//            .andExpect(jsonPath("$.responseDesc").value(DEFAULT_RESPONSE_DESC.toString()))
//            .andExpect(jsonPath("$.checks").value(DEFAULT_CHECKS.toString()))
//            .andExpect(jsonPath("$.proxys").value(DEFAULT_PROXYS.toString()))
//            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
//            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.etc").value(DEFAULT_ETC.toString()));
//    }
//
//    @Test
//    public void getNonExistingApiInfo() throws Exception {
//        // Get the apiInfo
//        restApiInfoMockMvc.perform(get("/api/api-infos/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateApiInfo() throws Exception {
//        // Initialize the database
//        apiInfoService.save(apiInfo);
//
//        int databaseSizeBeforeUpdate = apiInfoRepository.findAll().size();
//
//        // Update the apiInfo
//        ApiInfo updatedApiInfo = apiInfoRepository.findOne(apiInfo.getId());
//        updatedApiInfo
//                .description(UPDATED_DESCRIPTION)
//                .swaggerUri(UPDATED_SWAGGER_URI)
//                .version(UPDATED_VERSION)
//                .scheme(UPDATED_SCHEME)
//                .host(UPDATED_HOST)
//                .port(UPDATED_PORT)
//                .path(UPDATED_PATH)
//                .operation(UPDATED_OPERATION)
//                .cookieParams(UPDATED_COOKIE_PARAMS)
//                .formParams(UPDATED_FORM_PARAMS)
//                .queryParams(UPDATED_QUERY_PARAMS)
//                .pathParams(UPDATED_PATH_PARAMS)
//                .headParams(UPDATED_HEAD_PARAMS)
//                .bodyParams(UPDATED_BODY_PARAMS)
//                .requestDesc(UPDATED_REQUEST_DESC)
//                .responseDesc(UPDATED_RESPONSE_DESC)
//                .checks(UPDATED_CHECKS)
//                .proxys(UPDATED_PROXYS)
//                .projectId(UPDATED_PROJECT_ID)
//                .projectDescription(UPDATED_PROJECT_DESCRIPTION)
//                .etc(UPDATED_ETC);
//
//        restApiInfoMockMvc.perform(put("/api/api-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedApiInfo)))
//            .andExpect(status().isOk());
//
//        // Validate the ApiInfo in the database
//        List<ApiInfo> apiInfoList = apiInfoRepository.findAll();
//        assertThat(apiInfoList).hasSize(databaseSizeBeforeUpdate);
//        ApiInfo testApiInfo = apiInfoList.get(apiInfoList.size() - 1);
//        assertThat(testApiInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testApiInfo.getSwaggerUri()).isEqualTo(UPDATED_SWAGGER_URI);
//        assertThat(testApiInfo.getVersion()).isEqualTo(UPDATED_VERSION);
//        assertThat(testApiInfo.getScheme()).isEqualTo(UPDATED_SCHEME);
//        assertThat(testApiInfo.getHost()).isEqualTo(UPDATED_HOST);
//        assertThat(testApiInfo.getPort()).isEqualTo(UPDATED_PORT);
//        assertThat(testApiInfo.getPath()).isEqualTo(UPDATED_PATH);
//        assertThat(testApiInfo.getOperation()).isEqualTo(UPDATED_OPERATION);
//        assertThat(testApiInfo.getCookieParams()).isEqualTo(UPDATED_COOKIE_PARAMS);
//        assertThat(testApiInfo.getFormParams()).isEqualTo(UPDATED_FORM_PARAMS);
//        assertThat(testApiInfo.getQueryParams()).isEqualTo(UPDATED_QUERY_PARAMS);
//        assertThat(testApiInfo.getPathParams()).isEqualTo(UPDATED_PATH_PARAMS);
//        assertThat(testApiInfo.getHeadParams()).isEqualTo(UPDATED_HEAD_PARAMS);
//        assertThat(testApiInfo.getBodyParams()).isEqualTo(UPDATED_BODY_PARAMS);
//        assertThat(testApiInfo.getRequestDesc()).isEqualTo(UPDATED_REQUEST_DESC);
//        assertThat(testApiInfo.getResponseDesc()).isEqualTo(UPDATED_RESPONSE_DESC);
//        assertThat(testApiInfo.getChecks()).isEqualTo(UPDATED_CHECKS);
//        assertThat(testApiInfo.getProxys()).isEqualTo(UPDATED_PROXYS);
//        assertThat(testApiInfo.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
//        assertThat(testApiInfo.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
//        assertThat(testApiInfo.getEtc()).isEqualTo(UPDATED_ETC);
//    }
//
//    @Test
//    public void updateNonExistingApiInfo() throws Exception {
//        int databaseSizeBeforeUpdate = apiInfoRepository.findAll().size();
//
//        // Create the ApiInfo
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restApiInfoMockMvc.perform(put("/api/api-infos")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(apiInfo)))
//            .andExpect(status().isCreated());
//
//        // Validate the ApiInfo in the database
//        List<ApiInfo> apiInfoList = apiInfoRepository.findAll();
//        assertThat(apiInfoList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    public void deleteApiInfo() throws Exception {
//        // Initialize the database
//        apiInfoService.save(apiInfo);
//
//        int databaseSizeBeforeDelete = apiInfoRepository.findAll().size();
//
//        // Get the apiInfo
//        restApiInfoMockMvc.perform(delete("/api/api-infos/{id}", apiInfo.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<ApiInfo> apiInfoList = apiInfoRepository.findAll();
//        assertThat(apiInfoList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
