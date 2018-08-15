package de.vermietet.ecounter.service.impl;

import de.vermietet.ecounter.service.CounterService;
import de.vermietet.ecounter.domain.Counter;
import de.vermietet.ecounter.repository.CounterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
/**
 * Service Implementation for managing Counter.
 */
@Service
@Transactional
public class CounterServiceImpl implements CounterService {

    private final Logger log = LoggerFactory.getLogger(CounterServiceImpl.class);

    private final CounterRepository counterRepository;

    public CounterServiceImpl(CounterRepository counterRepository) {
        this.counterRepository = counterRepository;
    }

    /**
     * Save a counter.
     *
     * @param counter the entity to save
     * @return the persisted entity
     */
    @Override
    public Counter save(Counter counter) {
        log.debug("Request to save Counter : {}", counter);        return counterRepository.save(counter);
    }

    /**
     * Get all the counters.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Counter> findAll() {
        log.debug("Request to get all Counters");
        return counterRepository.findAll();
    }


    /**
     * Get one counter by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Counter> findOne(Long id) {
        log.debug("Request to get Counter : {}", id);
        return counterRepository.findById(id);
    }

    /**
     * Delete the counter by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Counter : {}", id);
        counterRepository.deleteById(id);
    }
}
