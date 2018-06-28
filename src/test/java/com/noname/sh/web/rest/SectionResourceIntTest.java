package com.noname.sh.web.rest;

import com.noname.sh.ShApp;

import com.noname.sh.domain.Section;
import com.noname.sh.domain.Question;
import com.noname.sh.repository.SectionRepository;
import com.noname.sh.repository.search.SectionSearchRepository;
import com.noname.sh.service.SectionService;
import com.noname.sh.service.dto.SectionDTO;
import com.noname.sh.service.mapper.SectionMapper;
import com.noname.sh.web.rest.errors.ExceptionTranslator;
import com.noname.sh.service.dto.SectionCriteria;
import com.noname.sh.service.SectionQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.noname.sh.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SectionResource REST controller.
 *
 * @see SectionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShApp.class)
public class SectionResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_AUDIO_NAME = "AAAAAAAAAA";
    private static final String UPDATED_AUDIO_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_TAG = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_TAG = "BBBBBBBBBB";

    private static final Long DEFAULT_PART_NUMBER = 1L;
    private static final Long UPDATED_PART_NUMBER = 2L;

    @Autowired
    private SectionRepository sectionRepository;


    @Autowired
    private SectionMapper sectionMapper;
    

    @Autowired
    private SectionService sectionService;

    /**
     * This repository is mocked in the com.noname.sh.repository.search test package.
     *
     * @see com.noname.sh.repository.search.SectionSearchRepositoryMockConfiguration
     */
    @Autowired
    private SectionSearchRepository mockSectionSearchRepository;

    @Autowired
    private SectionQueryService sectionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSectionMockMvc;

    private Section section;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SectionResource sectionResource = new SectionResource(sectionService, sectionQueryService);
        this.restSectionMockMvc = MockMvcBuilders.standaloneSetup(sectionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Section createEntity(EntityManager em) {
        Section section = new Section()
            .text(DEFAULT_TEXT)
            .audioName(DEFAULT_AUDIO_NAME)
            .imageName(DEFAULT_IMAGE_NAME)
            .imageTag(DEFAULT_IMAGE_TAG)
            .partNumber(DEFAULT_PART_NUMBER);
        return section;
    }

    @Before
    public void initTest() {
        section = createEntity(em);
    }

    @Test
    @Transactional
    public void createSection() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isCreated());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate + 1);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testSection.getAudioName()).isEqualTo(DEFAULT_AUDIO_NAME);
        assertThat(testSection.getImageName()).isEqualTo(DEFAULT_IMAGE_NAME);
        assertThat(testSection.getImageTag()).isEqualTo(DEFAULT_IMAGE_TAG);
        assertThat(testSection.getPartNumber()).isEqualTo(DEFAULT_PART_NUMBER);

        // Validate the Section in Elasticsearch
        verify(mockSectionSearchRepository, times(1)).save(testSection);
    }

    @Test
    @Transactional
    public void createSectionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sectionRepository.findAll().size();

        // Create the Section with an existing ID
        section.setId(1L);
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeCreate);

        // Validate the Section in Elasticsearch
        verify(mockSectionSearchRepository, times(0)).save(section);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionRepository.findAll().size();
        // set the field null
        section.setText(null);

        // Create the Section, which fails.
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPartNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = sectionRepository.findAll().size();
        // set the field null
        section.setPartNumber(null);

        // Create the Section, which fails.
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        restSectionMockMvc.perform(post("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSections() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].audioName").value(hasItem(DEFAULT_AUDIO_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageTag").value(hasItem(DEFAULT_IMAGE_TAG.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.intValue())));
    }
    

    @Test
    @Transactional
    public void getSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(section.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.audioName").value(DEFAULT_AUDIO_NAME.toString()))
            .andExpect(jsonPath("$.imageName").value(DEFAULT_IMAGE_NAME.toString()))
            .andExpect(jsonPath("$.imageTag").value(DEFAULT_IMAGE_TAG.toString()))
            .andExpect(jsonPath("$.partNumber").value(DEFAULT_PART_NUMBER.intValue()));
    }

    @Test
    @Transactional
    public void getAllSectionsByTextIsEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where text equals to DEFAULT_TEXT
        defaultSectionShouldBeFound("text.equals=" + DEFAULT_TEXT);

        // Get all the sectionList where text equals to UPDATED_TEXT
        defaultSectionShouldNotBeFound("text.equals=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllSectionsByTextIsInShouldWork() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where text in DEFAULT_TEXT or UPDATED_TEXT
        defaultSectionShouldBeFound("text.in=" + DEFAULT_TEXT + "," + UPDATED_TEXT);

        // Get all the sectionList where text equals to UPDATED_TEXT
        defaultSectionShouldNotBeFound("text.in=" + UPDATED_TEXT);
    }

    @Test
    @Transactional
    public void getAllSectionsByTextIsNullOrNotNull() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where text is not null
        defaultSectionShouldBeFound("text.specified=true");

        // Get all the sectionList where text is null
        defaultSectionShouldNotBeFound("text.specified=false");
    }

    @Test
    @Transactional
    public void getAllSectionsByAudioNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where audioName equals to DEFAULT_AUDIO_NAME
        defaultSectionShouldBeFound("audioName.equals=" + DEFAULT_AUDIO_NAME);

        // Get all the sectionList where audioName equals to UPDATED_AUDIO_NAME
        defaultSectionShouldNotBeFound("audioName.equals=" + UPDATED_AUDIO_NAME);
    }

    @Test
    @Transactional
    public void getAllSectionsByAudioNameIsInShouldWork() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where audioName in DEFAULT_AUDIO_NAME or UPDATED_AUDIO_NAME
        defaultSectionShouldBeFound("audioName.in=" + DEFAULT_AUDIO_NAME + "," + UPDATED_AUDIO_NAME);

        // Get all the sectionList where audioName equals to UPDATED_AUDIO_NAME
        defaultSectionShouldNotBeFound("audioName.in=" + UPDATED_AUDIO_NAME);
    }

    @Test
    @Transactional
    public void getAllSectionsByAudioNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where audioName is not null
        defaultSectionShouldBeFound("audioName.specified=true");

        // Get all the sectionList where audioName is null
        defaultSectionShouldNotBeFound("audioName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSectionsByImageNameIsEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageName equals to DEFAULT_IMAGE_NAME
        defaultSectionShouldBeFound("imageName.equals=" + DEFAULT_IMAGE_NAME);

        // Get all the sectionList where imageName equals to UPDATED_IMAGE_NAME
        defaultSectionShouldNotBeFound("imageName.equals=" + UPDATED_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void getAllSectionsByImageNameIsInShouldWork() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageName in DEFAULT_IMAGE_NAME or UPDATED_IMAGE_NAME
        defaultSectionShouldBeFound("imageName.in=" + DEFAULT_IMAGE_NAME + "," + UPDATED_IMAGE_NAME);

        // Get all the sectionList where imageName equals to UPDATED_IMAGE_NAME
        defaultSectionShouldNotBeFound("imageName.in=" + UPDATED_IMAGE_NAME);
    }

    @Test
    @Transactional
    public void getAllSectionsByImageNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageName is not null
        defaultSectionShouldBeFound("imageName.specified=true");

        // Get all the sectionList where imageName is null
        defaultSectionShouldNotBeFound("imageName.specified=false");
    }

    @Test
    @Transactional
    public void getAllSectionsByImageTagIsEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageTag equals to DEFAULT_IMAGE_TAG
        defaultSectionShouldBeFound("imageTag.equals=" + DEFAULT_IMAGE_TAG);

        // Get all the sectionList where imageTag equals to UPDATED_IMAGE_TAG
        defaultSectionShouldNotBeFound("imageTag.equals=" + UPDATED_IMAGE_TAG);
    }

    @Test
    @Transactional
    public void getAllSectionsByImageTagIsInShouldWork() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageTag in DEFAULT_IMAGE_TAG or UPDATED_IMAGE_TAG
        defaultSectionShouldBeFound("imageTag.in=" + DEFAULT_IMAGE_TAG + "," + UPDATED_IMAGE_TAG);

        // Get all the sectionList where imageTag equals to UPDATED_IMAGE_TAG
        defaultSectionShouldNotBeFound("imageTag.in=" + UPDATED_IMAGE_TAG);
    }

    @Test
    @Transactional
    public void getAllSectionsByImageTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where imageTag is not null
        defaultSectionShouldBeFound("imageTag.specified=true");

        // Get all the sectionList where imageTag is null
        defaultSectionShouldNotBeFound("imageTag.specified=false");
    }

    @Test
    @Transactional
    public void getAllSectionsByPartNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where partNumber equals to DEFAULT_PART_NUMBER
        defaultSectionShouldBeFound("partNumber.equals=" + DEFAULT_PART_NUMBER);

        // Get all the sectionList where partNumber equals to UPDATED_PART_NUMBER
        defaultSectionShouldNotBeFound("partNumber.equals=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSectionsByPartNumberIsInShouldWork() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where partNumber in DEFAULT_PART_NUMBER or UPDATED_PART_NUMBER
        defaultSectionShouldBeFound("partNumber.in=" + DEFAULT_PART_NUMBER + "," + UPDATED_PART_NUMBER);

        // Get all the sectionList where partNumber equals to UPDATED_PART_NUMBER
        defaultSectionShouldNotBeFound("partNumber.in=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSectionsByPartNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where partNumber is not null
        defaultSectionShouldBeFound("partNumber.specified=true");

        // Get all the sectionList where partNumber is null
        defaultSectionShouldNotBeFound("partNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllSectionsByPartNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where partNumber greater than or equals to DEFAULT_PART_NUMBER
        defaultSectionShouldBeFound("partNumber.greaterOrEqualThan=" + DEFAULT_PART_NUMBER);

        // Get all the sectionList where partNumber greater than or equals to UPDATED_PART_NUMBER
        defaultSectionShouldNotBeFound("partNumber.greaterOrEqualThan=" + UPDATED_PART_NUMBER);
    }

    @Test
    @Transactional
    public void getAllSectionsByPartNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        // Get all the sectionList where partNumber less than or equals to DEFAULT_PART_NUMBER
        defaultSectionShouldNotBeFound("partNumber.lessThan=" + DEFAULT_PART_NUMBER);

        // Get all the sectionList where partNumber less than or equals to UPDATED_PART_NUMBER
        defaultSectionShouldBeFound("partNumber.lessThan=" + UPDATED_PART_NUMBER);
    }


    @Test
    @Transactional
    public void getAllSectionsByQuestionIsEqualToSomething() throws Exception {
        // Initialize the database
        Question question = QuestionResourceIntTest.createEntity(em);
        em.persist(question);
        em.flush();
        section.addQuestion(question);
        sectionRepository.saveAndFlush(section);
        Long questionId = question.getId();

        // Get all the sectionList where question equals to questionId
        defaultSectionShouldBeFound("questionId.equals=" + questionId);

        // Get all the sectionList where question equals to questionId + 1
        defaultSectionShouldNotBeFound("questionId.equals=" + (questionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultSectionShouldBeFound(String filter) throws Exception {
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].audioName").value(hasItem(DEFAULT_AUDIO_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageTag").value(hasItem(DEFAULT_IMAGE_TAG.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultSectionShouldNotBeFound(String filter) throws Exception {
        restSectionMockMvc.perform(get("/api/sections?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @Transactional
    public void getNonExistingSection() throws Exception {
        // Get the section
        restSectionMockMvc.perform(get("/api/sections/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Update the section
        Section updatedSection = sectionRepository.findById(section.getId()).get();
        // Disconnect from session so that the updates on updatedSection are not directly saved in db
        em.detach(updatedSection);
        updatedSection
            .text(UPDATED_TEXT)
            .audioName(UPDATED_AUDIO_NAME)
            .imageName(UPDATED_IMAGE_NAME)
            .imageTag(UPDATED_IMAGE_TAG)
            .partNumber(UPDATED_PART_NUMBER);
        SectionDTO sectionDTO = sectionMapper.toDto(updatedSection);

        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isOk());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);
        Section testSection = sectionList.get(sectionList.size() - 1);
        assertThat(testSection.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testSection.getAudioName()).isEqualTo(UPDATED_AUDIO_NAME);
        assertThat(testSection.getImageName()).isEqualTo(UPDATED_IMAGE_NAME);
        assertThat(testSection.getImageTag()).isEqualTo(UPDATED_IMAGE_TAG);
        assertThat(testSection.getPartNumber()).isEqualTo(UPDATED_PART_NUMBER);

        // Validate the Section in Elasticsearch
        verify(mockSectionSearchRepository, times(1)).save(testSection);
    }

    @Test
    @Transactional
    public void updateNonExistingSection() throws Exception {
        int databaseSizeBeforeUpdate = sectionRepository.findAll().size();

        // Create the Section
        SectionDTO sectionDTO = sectionMapper.toDto(section);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSectionMockMvc.perform(put("/api/sections")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sectionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Section in the database
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Section in Elasticsearch
        verify(mockSectionSearchRepository, times(0)).save(section);
    }

    @Test
    @Transactional
    public void deleteSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);

        int databaseSizeBeforeDelete = sectionRepository.findAll().size();

        // Get the section
        restSectionMockMvc.perform(delete("/api/sections/{id}", section.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Section> sectionList = sectionRepository.findAll();
        assertThat(sectionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Section in Elasticsearch
        verify(mockSectionSearchRepository, times(1)).deleteById(section.getId());
    }

    @Test
    @Transactional
    public void searchSection() throws Exception {
        // Initialize the database
        sectionRepository.saveAndFlush(section);
        when(mockSectionSearchRepository.search(queryStringQuery("id:" + section.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(section), PageRequest.of(0, 1), 1));
        // Search the section
        restSectionMockMvc.perform(get("/api/_search/sections?query=id:" + section.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(section.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].audioName").value(hasItem(DEFAULT_AUDIO_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageName").value(hasItem(DEFAULT_IMAGE_NAME.toString())))
            .andExpect(jsonPath("$.[*].imageTag").value(hasItem(DEFAULT_IMAGE_TAG.toString())))
            .andExpect(jsonPath("$.[*].partNumber").value(hasItem(DEFAULT_PART_NUMBER.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Section.class);
        Section section1 = new Section();
        section1.setId(1L);
        Section section2 = new Section();
        section2.setId(section1.getId());
        assertThat(section1).isEqualTo(section2);
        section2.setId(2L);
        assertThat(section1).isNotEqualTo(section2);
        section1.setId(null);
        assertThat(section1).isNotEqualTo(section2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SectionDTO.class);
        SectionDTO sectionDTO1 = new SectionDTO();
        sectionDTO1.setId(1L);
        SectionDTO sectionDTO2 = new SectionDTO();
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO2.setId(sectionDTO1.getId());
        assertThat(sectionDTO1).isEqualTo(sectionDTO2);
        sectionDTO2.setId(2L);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
        sectionDTO1.setId(null);
        assertThat(sectionDTO1).isNotEqualTo(sectionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(sectionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(sectionMapper.fromId(null)).isNull();
    }
}
