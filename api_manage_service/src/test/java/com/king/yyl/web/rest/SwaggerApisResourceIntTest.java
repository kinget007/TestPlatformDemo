//package com.king.yyl.web.rest;
//
//import com.king.yyl.ApimanageserviceApp;
//
//import com.king.yyl.config.SecurityBeanOverrideConfiguration;
//
//import com.king.yyl.domain.SwaggerApis;
//import com.king.yyl.repository.SwaggerApisRepository;
//import com.king.yyl.service.SwaggerApisService;
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
// * Test class for the SwaggerApisResource REST controller.
// *
// * @see SwaggerApisResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ApimanageserviceApp.class, SecurityBeanOverrideConfiguration.class})
//public class SwaggerApisResourceIntTest {
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SWAGGER_URI = "AAAAAAAAAA";
//    private static final String UPDATED_SWAGGER_URI = "BBBBBBBBBB";
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
//    private SwaggerApisRepository swaggerApisRepository;
//
//    @Inject
//    private SwaggerApisService swaggerApisService;
//
//    @Inject
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Inject
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    private MockMvc restSwaggerApisMockMvc;
//
//    private SwaggerApis swaggerApis;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        SwaggerApisResource swaggerApisResource = new SwaggerApisResource();
//        ReflectionTestUtils.setField(swaggerApisResource, "swaggerApisService", swaggerApisService);
//        this.restSwaggerApisMockMvc = MockMvcBuilders.standaloneSetup(swaggerApisResource)
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
//    public static SwaggerApis createEntity() {
//        SwaggerApis swaggerApis = new SwaggerApis()
//                .description(DEFAULT_DESCRIPTION)
//                .swaggerUri(DEFAULT_SWAGGER_URI)
//                .projectId(DEFAULT_PROJECT_ID)
//                .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
//                .etc(DEFAULT_ETC);
//        return swaggerApis;
//    }
//
//    @Before
//    public void initTest() {
//        swaggerApisRepository.deleteAll();
//        swaggerApis = createEntity();
//    }
//
//    @Test
//    public void createSwaggerApis() throws Exception {
//        int databaseSizeBeforeCreate = swaggerApisRepository.findAll().size();
//
//        // Create the SwaggerApis
//
//        restSwaggerApisMockMvc.perform(post("/api/swagger-apis")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(swaggerApis)))
//            .andExpect(status().isCreated());
//
//        // Validate the SwaggerApis in the database
//        List<SwaggerApis> swaggerApisList = swaggerApisRepository.findAll();
//        assertThat(swaggerApisList).hasSize(databaseSizeBeforeCreate + 1);
//        SwaggerApis testSwaggerApis = swaggerApisList.get(swaggerApisList.size() - 1);
//        assertThat(testSwaggerApis.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testSwaggerApis.getSwaggerUri()).isEqualTo(DEFAULT_SWAGGER_URI);
//        assertThat(testSwaggerApis.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
//        assertThat(testSwaggerApis.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
//        assertThat(testSwaggerApis.getEtc()).isEqualTo(DEFAULT_ETC);
//    }
//
//    @Test
//    public void createSwaggerApisWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = swaggerApisRepository.findAll().size();
//
//        // Create the SwaggerApis with an existing ID
//        SwaggerApis existingSwaggerApis = new SwaggerApis();
//        existingSwaggerApis.setId("existing_id");
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restSwaggerApisMockMvc.perform(post("/api/swagger-apis")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(existingSwaggerApis)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<SwaggerApis> swaggerApisList = swaggerApisRepository.findAll();
//        assertThat(swaggerApisList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    public void getAllSwaggerApis() throws Exception {
//        // Initialize the database
//        swaggerApisRepository.save(swaggerApis);
//
//        // Get all the swaggerApisList
//        restSwaggerApisMockMvc.perform(get("/api/swagger-apis?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(swaggerApis.getId())))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].swaggerUri").value(hasItem(DEFAULT_SWAGGER_URI.toString())))
//            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
//            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].etc").value(hasItem(DEFAULT_ETC.toString())));
//    }
//
//    @Test
//    public void getSwaggerApis() throws Exception {
//        // Initialize the database
//        swaggerApisRepository.save(swaggerApis);
//
//        // Get the swaggerApis
//        restSwaggerApisMockMvc.perform(get("/api/swagger-apis/{id}", swaggerApis.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(swaggerApis.getId()))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.swaggerUri").value(DEFAULT_SWAGGER_URI.toString()))
//            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
//            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.etc").value(DEFAULT_ETC.toString()));
//    }
//
//    @Test
//    public void getNonExistingSwaggerApis() throws Exception {
//        // Get the swaggerApis
//        restSwaggerApisMockMvc.perform(get("/api/swagger-apis/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateSwaggerApis() throws Exception {
//        // Initialize the database
//        swaggerApisService.save(swaggerApis);
//
//        int databaseSizeBeforeUpdate = swaggerApisRepository.findAll().size();
//
//        // Update the swaggerApis
//        SwaggerApis updatedSwaggerApis = swaggerApisRepository.findOne(swaggerApis.getId());
//        updatedSwaggerApis
//                .description(UPDATED_DESCRIPTION)
//                .swaggerUri(UPDATED_SWAGGER_URI)
//                .projectId(UPDATED_PROJECT_ID)
//                .projectDescription(UPDATED_PROJECT_DESCRIPTION)
//                .etc(UPDATED_ETC);
//
//        restSwaggerApisMockMvc.perform(put("/api/swagger-apis")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedSwaggerApis)))
//            .andExpect(status().isOk());
//
//        // Validate the SwaggerApis in the database
//        List<SwaggerApis> swaggerApisList = swaggerApisRepository.findAll();
//        assertThat(swaggerApisList).hasSize(databaseSizeBeforeUpdate);
//        SwaggerApis testSwaggerApis = swaggerApisList.get(swaggerApisList.size() - 1);
//        assertThat(testSwaggerApis.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testSwaggerApis.getSwaggerUri()).isEqualTo(UPDATED_SWAGGER_URI);
//        assertThat(testSwaggerApis.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
//        assertThat(testSwaggerApis.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
//        assertThat(testSwaggerApis.getEtc()).isEqualTo(UPDATED_ETC);
//    }
//
//    @Test
//    public void updateNonExistingSwaggerApis() throws Exception {
//        int databaseSizeBeforeUpdate = swaggerApisRepository.findAll().size();
//
//        // Create the SwaggerApis
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restSwaggerApisMockMvc.perform(put("/api/swagger-apis")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(swaggerApis)))
//            .andExpect(status().isCreated());
//
//        // Validate the SwaggerApis in the database
//        List<SwaggerApis> swaggerApisList = swaggerApisRepository.findAll();
//        assertThat(swaggerApisList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    public void deleteSwaggerApis() throws Exception {
//        // Initialize the database
//        swaggerApisService.save(swaggerApis);
//
//        int databaseSizeBeforeDelete = swaggerApisRepository.findAll().size();
//
//        // Get the swaggerApis
//        restSwaggerApisMockMvc.perform(delete("/api/swagger-apis/{id}", swaggerApis.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<SwaggerApis> swaggerApisList = swaggerApisRepository.findAll();
//        assertThat(swaggerApisList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
