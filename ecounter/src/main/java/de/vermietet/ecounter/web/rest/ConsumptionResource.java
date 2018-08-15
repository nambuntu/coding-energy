package de.vermietet.ecounter.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.vermietet.ecounter.domain.Consumption;
import de.vermietet.ecounter.exception.ConsumptionPeriodNotSuppoted;
import de.vermietet.ecounter.service.ConsumptionService;
import de.vermietet.ecounter.web.rest.errors.BadRequestAlertException;
import de.vermietet.ecounter.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing Consumption.
 */
@RestController
@RequestMapping("/api")
public class ConsumptionResource {

    private final Logger log = LoggerFactory.getLogger(ConsumptionResource.class);

    private static final String ENTITY_NAME = "consumption";

    private final ConsumptionService consumptionService;

    public ConsumptionResource(ConsumptionService consumptionService) {
        this.consumptionService = consumptionService;
    }

    /**
     * POST  /consumptions : Create a new consumption.
     *
     * @param consumption the consumption to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consumption, or with status 400 (Bad Request) if the consumption has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consumptions")
    @Timed
    public ResponseEntity<Consumption> createConsumption(@Valid @RequestBody Consumption consumption) throws URISyntaxException {
        log.debug("REST request to save Consumption : {}", consumption);
        if (consumption.getId() != null) {
            throw new BadRequestAlertException("A new consumption cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Consumption result = consumptionService.save(consumption);
        return ResponseEntity.created(new URI("/api/consumptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consumptions : get all the consumptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consumptions in body
     */
    @GetMapping("/consumptions")
    @Timed
    public List<Consumption> getAllConsumptions() {
        log.debug("REST request to get all Consumptions");
        return consumptionService.findAll();
    }

    @GetMapping(value = "consumption_report")
    public List<Consumption> getConsumptionsByPeriod(@RequestParam("duration") String duration) throws ConsumptionPeriodNotSuppoted {
        log.debug("REST request for {} duration Consumption", duration);

        return consumptionService.findByPeriod(duration);
    }
}
