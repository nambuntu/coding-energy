package de.vermietet.ecounter.web.rest;

import de.vermietet.ecounter.EcounterApp;

import de.vermietet.ecounter.domain.Consumption;
import de.vermietet.ecounter.repository.ConsumptionRepository;
import de.vermietet.ecounter.service.ConsumptionService;
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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;


import static de.vermietet.ecounter.web.rest.TestUtil.sameInstant;
import static de.vermietet.ecounter.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConsumptionResource REST controller.
 *
 * @see ConsumptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EcounterApp.class)
public class ConsumptionResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final ZonedDateTime DEFAULT_DATE_CREATED = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATE_CREATED = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private ConsumptionRepository consumptionRepository;

    

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsumptionMockMvc;

    private Consumption consumption;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsumptionResource consumptionResource = new ConsumptionResource(consumptionService);
        this.restConsumptionMockMvc = MockMvcBuilders.standaloneSetup(consumptionResource)
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
    public static Consumption createEntity(EntityManager em) {
        Consumption consumption = new Consumption()
            .amount(DEFAULT_AMOUNT)
            .dateCreated(DEFAULT_DATE_CREATED);
        return consumption;
    }

    @Before
    public void initTest() {
        consumption = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsumption() throws Exception {
        int databaseSizeBeforeCreate = consumptionRepository.findAll().size();

        // Create the Consumption
        restConsumptionMockMvc.perform(post("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isCreated());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeCreate + 1);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testConsumption.getDateCreated()).isEqualTo(DEFAULT_DATE_CREATED);
    }

    @Test
    @Transactional
    public void createConsumptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consumptionRepository.findAll().size();

        // Create the Consumption with an existing ID
        consumption.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsumptionMockMvc.perform(post("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = consumptionRepository.findAll().size();
        // set the field null
        consumption.setAmount(null);

        // Create the Consumption, which fails.

        restConsumptionMockMvc.perform(post("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isBadRequest());

        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateCreatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = consumptionRepository.findAll().size();
        // set the field null
        consumption.setDateCreated(null);

        // Create the Consumption, which fails.

        restConsumptionMockMvc.perform(post("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isBadRequest());

        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConsumptions() throws Exception {
        // Initialize the database
        consumptionRepository.saveAndFlush(consumption);

        // Get all the consumptionList
        restConsumptionMockMvc.perform(get("/api/consumptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consumption.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].dateCreated").value(hasItem(sameInstant(DEFAULT_DATE_CREATED))));
    }
    

    @Test
    @Transactional
    public void getConsumption() throws Exception {
        // Initialize the database
        consumptionRepository.saveAndFlush(consumption);

        // Get the consumption
        restConsumptionMockMvc.perform(get("/api/consumptions/{id}", consumption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consumption.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.dateCreated").value(sameInstant(DEFAULT_DATE_CREATED)));
    }
    @Test
    @Transactional
    public void getNonExistingConsumption() throws Exception {
        // Get the consumption
        restConsumptionMockMvc.perform(get("/api/consumptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsumption() throws Exception {
        // Initialize the database
        consumptionService.save(consumption);

        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();

        // Update the consumption
        Consumption updatedConsumption = consumptionRepository.findById(consumption.getId()).get();
        // Disconnect from session so that the updates on updatedConsumption are not directly saved in db
        em.detach(updatedConsumption);
        updatedConsumption
            .amount(UPDATED_AMOUNT)
            .dateCreated(UPDATED_DATE_CREATED);

        restConsumptionMockMvc.perform(put("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConsumption)))
            .andExpect(status().isOk());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
        Consumption testConsumption = consumptionList.get(consumptionList.size() - 1);
        assertThat(testConsumption.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testConsumption.getDateCreated()).isEqualTo(UPDATED_DATE_CREATED);
    }

    @Test
    @Transactional
    public void updateNonExistingConsumption() throws Exception {
        int databaseSizeBeforeUpdate = consumptionRepository.findAll().size();

        // Create the Consumption

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsumptionMockMvc.perform(put("/api/consumptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consumption)))
            .andExpect(status().isBadRequest());

        // Validate the Consumption in the database
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConsumption() throws Exception {
        // Initialize the database
        consumptionService.save(consumption);

        int databaseSizeBeforeDelete = consumptionRepository.findAll().size();

        // Get the consumption
        restConsumptionMockMvc.perform(delete("/api/consumptions/{id}", consumption.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consumption> consumptionList = consumptionRepository.findAll();
        assertThat(consumptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consumption.class);
        Consumption consumption1 = new Consumption();
        consumption1.setId(1L);
        Consumption consumption2 = new Consumption();
        consumption2.setId(consumption1.getId());
        assertThat(consumption1).isEqualTo(consumption2);
        consumption2.setId(2L);
        assertThat(consumption1).isNotEqualTo(consumption2);
        consumption1.setId(null);
        assertThat(consumption1).isNotEqualTo(consumption2);
    }
}
