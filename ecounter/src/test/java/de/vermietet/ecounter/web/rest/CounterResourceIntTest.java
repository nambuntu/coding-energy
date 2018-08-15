package de.vermietet.ecounter.web.rest;

import de.vermietet.ecounter.EcounterApp;

import de.vermietet.ecounter.domain.Counter;
import de.vermietet.ecounter.repository.CounterRepository;
import de.vermietet.ecounter.service.CounterService;
import de.vermietet.ecounter.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static de.vermietet.ecounter.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CounterResource REST controller.
 *
 * @see CounterResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcounterApp.class)
public class CounterResourceIntTest {

    private static final String DEFAULT_VILLAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_VILLAGE_NAME = "BBBBBBBBBB";

    @Autowired
    private CounterRepository counterRepository;

    

    @Autowired
    private CounterService counterService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCounterMockMvc;

    private Counter counter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CounterResource counterResource = new CounterResource(counterService);
        this.restCounterMockMvc = MockMvcBuilders.standaloneSetup(counterResource)
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
    public static Counter createEntity(EntityManager em) {
        Counter counter = new Counter()
            .villageName(DEFAULT_VILLAGE_NAME);
        return counter;
    }

    @Before
    public void initTest() {
        counter = createEntity(em);
    }

    @Test
    @Transactional
    public void createCounter() throws Exception {
        int databaseSizeBeforeCreate = counterRepository.findAll().size();

        // Create the Counter
        restCounterMockMvc.perform(post("/api/counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(counter)))
            .andExpect(status().isCreated());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeCreate + 1);
        Counter testCounter = counterList.get(counterList.size() - 1);
        assertThat(testCounter.getVillageName()).isEqualTo(DEFAULT_VILLAGE_NAME);
    }

    @Test
    @Transactional
    public void createCounterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = counterRepository.findAll().size();

        // Create the Counter with an existing ID
        counter.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCounterMockMvc.perform(post("/api/counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(counter)))
            .andExpect(status().isBadRequest());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkVillageNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = counterRepository.findAll().size();
        // set the field null
        counter.setVillageName(null);

        // Create the Counter, which fails.

        restCounterMockMvc.perform(post("/api/counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(counter)))
            .andExpect(status().isBadRequest());

        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCounters() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get all the counterList
        restCounterMockMvc.perform(get("/api/counters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counter.getId().intValue())))
            .andExpect(jsonPath("$.[*].villageName").value(hasItem(DEFAULT_VILLAGE_NAME.toString())));
    }
    

    @Test
    @Transactional
    public void getCounter() throws Exception {
        // Initialize the database
        counterRepository.saveAndFlush(counter);

        // Get the counter
        restCounterMockMvc.perform(get("/api/counters/{id}", counter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(counter.getId().intValue()))
            .andExpect(jsonPath("$.villageName").value(DEFAULT_VILLAGE_NAME.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingCounter() throws Exception {
        // Get the counter
        restCounterMockMvc.perform(get("/api/counters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCounter() throws Exception {
        // Initialize the database
        counterService.save(counter);

        int databaseSizeBeforeUpdate = counterRepository.findAll().size();

        // Update the counter
        Counter updatedCounter = counterRepository.findById(counter.getId()).get();
        // Disconnect from session so that the updates on updatedCounter are not directly saved in db
        em.detach(updatedCounter);
        updatedCounter
            .villageName(UPDATED_VILLAGE_NAME);

        restCounterMockMvc.perform(put("/api/counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCounter)))
            .andExpect(status().isOk());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeUpdate);
        Counter testCounter = counterList.get(counterList.size() - 1);
        assertThat(testCounter.getVillageName()).isEqualTo(UPDATED_VILLAGE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingCounter() throws Exception {
        int databaseSizeBeforeUpdate = counterRepository.findAll().size();

        // Create the Counter

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCounterMockMvc.perform(put("/api/counters")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(counter)))
            .andExpect(status().isBadRequest());

        // Validate the Counter in the database
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCounter() throws Exception {
        // Initialize the database
        counterService.save(counter);

        int databaseSizeBeforeDelete = counterRepository.findAll().size();

        // Get the counter
        restCounterMockMvc.perform(delete("/api/counters/{id}", counter.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Counter> counterList = counterRepository.findAll();
        assertThat(counterList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Counter.class);
        Counter counter1 = new Counter();
        counter1.setId(1L);
        Counter counter2 = new Counter();
        counter2.setId(counter1.getId());
        assertThat(counter1).isEqualTo(counter2);
        counter2.setId(2L);
        assertThat(counter1).isNotEqualTo(counter2);
        counter1.setId(null);
        assertThat(counter1).isNotEqualTo(counter2);
    }
}
