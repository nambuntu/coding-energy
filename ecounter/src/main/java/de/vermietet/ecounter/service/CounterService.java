package de.vermietet.ecounter.service;

import de.vermietet.ecounter.domain.Counter;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Counter.
 */
public interface CounterService {

    /**
     * Save a counter.
     *
     * @param counter the entity to save
     * @return the persisted entity
     */
    Counter save(Counter counter);

    /**
     * Get all the counters.
     *
     * @return the list of entities
     */
    List<Counter> findAll();


    /**
     * Get the "id" counter.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Counter> findOne(Long id);

    /**
     * Delete the "id" counter.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
