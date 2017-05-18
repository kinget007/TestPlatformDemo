//package com.king.yyl.web.rest;
//
//import com.king.yyl.ApimanageserviceApp;
//
//import com.king.yyl.config.SecurityBeanOverrideConfiguration;
//
//import com.king.yyl.domain.TestSuit;
//import com.king.yyl.repository.TestSuitRepository;
//import com.king.yyl.service.TestSuitService;
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
// * Test class for the TestSuitResource REST controller.
// *
// * @see TestSuitResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = {ApimanageserviceApp.class, SecurityBeanOverrideConfiguration.class})
//public class TestSuitResourceIntTest {
//
//    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
//    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";
//
//    private static final String DEFAULT_SWAGGER_URI = "AAAAAAAAAA";
//    private static final String UPDATED_SWAGGER_URI = "BBBBBBBBBB";
//
//    private static final String DEFAULT_DEPEND_ON = "AAAAAAAAAA";
//    private static final String UPDATED_DEPEND_ON = "BBBBBBBBBB";
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
//    private TestSuitRepository testSuitRepository;
//
//    @Inject
//    private TestSuitService testSuitService;
//
//    @Inject
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Inject
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    private MockMvc restTestSuitMockMvc;
//
//    private TestSuit testSuit;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        TestSuitResource testSuitResource = new TestSuitResource();
//        ReflectionTestUtils.setField(testSuitResource, "testSuitService", testSuitService);
//        this.restTestSuitMockMvc = MockMvcBuilders.standaloneSetup(testSuitResource)
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
//    public static TestSuit createEntity() {
//        TestSuit testSuit = new TestSuit()
//                .description(DEFAULT_DESCRIPTION)
//                .swaggerUri(DEFAULT_SWAGGER_URI)
//                .dependOn(DEFAULT_DEPEND_ON)
//                .projectId(DEFAULT_PROJECT_ID)
//                .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
//                .etc(DEFAULT_ETC);
//        return testSuit;
//    }
//
//    @Before
//    public void initTest() {
//        testSuitRepository.deleteAll();
//        testSuit = createEntity();
//    }
//
//    @Test
//    public void createTestSuit() throws Exception {
//        int databaseSizeBeforeCreate = testSuitRepository.findAll().size();
//
//        // Create the TestSuit
//
//        restTestSuitMockMvc.perform(post("/api/test-suits")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(testSuit)))
//            .andExpect(status().isCreated());
//
//        // Validate the TestSuit in the database
//        List<TestSuit> testSuitList = testSuitRepository.findAll();
//        assertThat(testSuitList).hasSize(databaseSizeBeforeCreate + 1);
//        TestSuit testTestSuit = testSuitList.get(testSuitList.size() - 1);
//        assertThat(testTestSuit.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
//        assertThat(testTestSuit.getSwaggerUri()).isEqualTo(DEFAULT_SWAGGER_URI);
//        assertThat(testTestSuit.getDependOn()).isEqualTo(DEFAULT_DEPEND_ON);
//        assertThat(testTestSuit.getProjectId()).isEqualTo(DEFAULT_PROJECT_ID);
//        assertThat(testTestSuit.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
//        assertThat(testTestSuit.getEtc()).isEqualTo(DEFAULT_ETC);
//    }
//
//    @Test
//    public void createTestSuitWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = testSuitRepository.findAll().size();
//
//        // Create the TestSuit with an existing ID
//        TestSuit existingTestSuit = new TestSuit();
//        existingTestSuit.setId("existing_id");
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restTestSuitMockMvc.perform(post("/api/test-suits")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(existingTestSuit)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<TestSuit> testSuitList = testSuitRepository.findAll();
//        assertThat(testSuitList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    public void getAllTestSuits() throws Exception {
//        // Initialize the database
//        testSuitRepository.save(testSuit);
//
//        // Get all the testSuitList
//        restTestSuitMockMvc.perform(get("/api/test-suits?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(testSuit.getId())))
//            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].swaggerUri").value(hasItem(DEFAULT_SWAGGER_URI.toString())))
//            .andExpect(jsonPath("$.[*].dependOn").value(hasItem(DEFAULT_DEPEND_ON.toString())))
//            .andExpect(jsonPath("$.[*].projectId").value(hasItem(DEFAULT_PROJECT_ID.toString())))
//            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION.toString())))
//            .andExpect(jsonPath("$.[*].etc").value(hasItem(DEFAULT_ETC.toString())));
//    }
//
//    @Test
//    public void getTestSuit() throws Exception {
//        // Initialize the database
//        testSuitRepository.save(testSuit);
//
//        // Get the testSuit
//        restTestSuitMockMvc.perform(get("/api/test-suits/{id}", testSuit.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(testSuit.getId()))
//            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.swaggerUri").value(DEFAULT_SWAGGER_URI.toString()))
//            .andExpect(jsonPath("$.dependOn").value(DEFAULT_DEPEND_ON.toString()))
//            .andExpect(jsonPath("$.projectId").value(DEFAULT_PROJECT_ID.toString()))
//            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION.toString()))
//            .andExpect(jsonPath("$.etc").value(DEFAULT_ETC.toString()));
//    }
//
//    @Test
//    public void getNonExistingTestSuit() throws Exception {
//        // Get the testSuit
//        restTestSuitMockMvc.perform(get("/api/test-suits/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void updateTestSuit() throws Exception {
//        // Initialize the database
//        testSuitService.save(testSuit);
//
//        int databaseSizeBeforeUpdate = testSuitRepository.findAll().size();
//
//        // Update the testSuit
//        TestSuit updatedTestSuit = testSuitRepository.findOne(testSuit.getId());
//        updatedTestSuit
//                .description(UPDATED_DESCRIPTION)
//                .swaggerUri(UPDATED_SWAGGER_URI)
//                .dependOn(UPDATED_DEPEND_ON)
//                .projectId(UPDATED_PROJECT_ID)
//                .projectDescription(UPDATED_PROJECT_DESCRIPTION)
//                .etc(UPDATED_ETC);
//
//        restTestSuitMockMvc.perform(put("/api/test-suits")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedTestSuit)))
//            .andExpect(status().isOk());
//
//        // Validate the TestSuit in the database
//        List<TestSuit> testSuitList = testSuitRepository.findAll();
//        assertThat(testSuitList).hasSize(databaseSizeBeforeUpdate);
//        TestSuit testTestSuit = testSuitList.get(testSuitList.size() - 1);
//        assertThat(testTestSuit.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
//        assertThat(testTestSuit.getSwaggerUri()).isEqualTo(UPDATED_SWAGGER_URI);
//        assertThat(testTestSuit.getDependOn()).isEqualTo(UPDATED_DEPEND_ON);
//        assertThat(testTestSuit.getProjectId()).isEqualTo(UPDATED_PROJECT_ID);
//        assertThat(testTestSuit.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
//        assertThat(testTestSuit.getEtc()).isEqualTo(UPDATED_ETC);
//    }
//
//    @Test
//    public void updateNonExistingTestSuit() throws Exception {
//        int databaseSizeBeforeUpdate = testSuitRepository.findAll().size();
//
//        // Create the TestSuit
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restTestSuitMockMvc.perform(put("/api/test-suits")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(testSuit)))
//            .andExpect(status().isCreated());
//
//        // Validate the TestSuit in the database
//        List<TestSuit> testSuitList = testSuitRepository.findAll();
//        assertThat(testSuitList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    public void deleteTestSuit() throws Exception {
//        // Initialize the database
//        testSuitService.save(testSuit);
//
//        int databaseSizeBeforeDelete = testSuitRepository.findAll().size();
//
//        // Get the testSuit
//        restTestSuitMockMvc.perform(delete("/api/test-suits/{id}", testSuit.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<TestSuit> testSuitList = testSuitRepository.findAll();
//        assertThat(testSuitList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//}
