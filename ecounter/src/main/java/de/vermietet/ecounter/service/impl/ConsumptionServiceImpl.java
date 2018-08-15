package de.vermietet.ecounter.service.impl;

import de.vermietet.ecounter.domain.Consumption;
import de.vermietet.ecounter.domain.ConsumptionPeriod;
import de.vermietet.ecounter.domain.PeriodMap;
import de.vermietet.ecounter.exception.ConsumptionPeriodNotSuppoted;
import de.vermietet.ecounter.repository.ConsumptionRepository;
import de.vermietet.ecounter.service.ConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Consumption.
 */
@Service
@Transactional
public class ConsumptionServiceImpl implements ConsumptionService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionServiceImpl.class);

    private final ConsumptionRepository consumptionRepository;

    public ConsumptionServiceImpl(ConsumptionRepository consumptionRepository) {
        this.consumptionRepository = consumptionRepository;
    }

    /**
     * Find all Consumption within a period of time, given by user
     *
     * @param period 24h, 12h or 30d
     * @return
     * @throws ConsumptionPeriodNotSuppoted if period is not in agreed list, check {@link PeriodMap}
     */
    @Override
    public List<Consumption> findByPeriod(String period) throws ConsumptionPeriodNotSuppoted {
        ConsumptionPeriod consumptionPeriod = PeriodMap.periodMap.get(period);
        if (null == consumptionPeriod) {
            throw new ConsumptionPeriodNotSuppoted(String.format("Specified period %s is not supported", period));
        }

        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime duration = now.minus(consumptionPeriod.getAmount(), consumptionPeriod.getUnit());
        log.debug("Find consumption by period from {}", duration);
        return consumptionRepository.findConsumptionByDateCreatedGreaterThanEqual(duration);
    }

    /**
     * Save a consumption.
     *
     * @param consumption the entity to save
     * @return the persisted entity
     */
    @Override
    public Consumption save(Consumption consumption) {
        log.debug("Request to save Consumption : {}", consumption);
        return consumptionRepository.save(consumption);
    }

    /**
     * Get all the consumptions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Consumption> findAll() {
        log.debug("Request to get all Consumptions");
        return consumptionRepository.findAll();
    }


    /**
     * Get one consumption by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Consumption> findOne(Long id) {
        log.debug("Request to get Consumption : {}", id);
        return consumptionRepository.findById(id);
    }

    /**
     * Delete the consumption by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consumption : {}", id);
        consumptionRepository.deleteById(id);
    }
}
