package de.vermietet.ecounter.service;

import de.vermietet.ecounter.domain.Consumption;
import de.vermietet.ecounter.exception.ConsumptionPeriodNotSuppoted;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Consumption.
 */
public interface ConsumptionService {

    List<Consumption> findByPeriod(String period) throws ConsumptionPeriodNotSuppoted;

    /**
     * Save a consumption.
     *
     * @param consumption the entity to save
     * @return the persisted entity
     */
    Consumption save(Consumption consumption);

    /**
     * Get all the consumptions.
     *
     * @return the list of entities
     */
    List<Consumption> findAll();


    /**
     * Get the "id" consumption.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Consumption> findOne(Long id);

    /**
     * Delete the "id" consumption.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
