package de.vermietet.ecounter.repository;

import de.vermietet.ecounter.domain.Consumption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;


/**
 * Spring Data  repository for the Consumption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsumptionRepository extends JpaRepository<Consumption, Long> {
    public List<Consumption> findConsumptionByDateCreatedGreaterThanEqual(ZonedDateTime date);
}
