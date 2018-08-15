package de.vermietet.ecounter.web.rest;

import com.codahale.metrics.annotation.Timed;
import de.vermietet.ecounter.domain.Counter;
import de.vermietet.ecounter.service.CounterService;
import de.vermietet.ecounter.web.rest.errors.BadRequestAlertException;
import de.vermietet.ecounter.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Counter.
 */
@RestController
@RequestMapping("/api")
public class CounterResource {

    private final Logger log = LoggerFactory.getLogger(CounterResource.class);

    private static final String ENTITY_NAME = "counter";

    private final CounterService counterService;

    public CounterResource(CounterService counterService) {
        this.counterService = counterService;
    }

    /**
     * POST  /counters : Create a new counter.
     *
     * @param counter the counter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new counter, or with status 400 (Bad Request) if the counter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/counters")
    @Timed
    public ResponseEntity<Counter> createCounter(@Valid @RequestBody Counter counter) throws URISyntaxException {
        log.debug("REST request to save Counter : {}", counter);
        if (counter.getId() != null) {
            throw new BadRequestAlertException("A new counter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Counter result = counterService.save(counter);
        return ResponseEntity.created(new URI("/api/counters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /counters : Updates an existing counter.
     *
     * @param counter the counter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated counter,
     * or with status 400 (Bad Request) if the counter is not valid,
     * or with status 500 (Internal Server Error) if the counter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/counters")
    @Timed
    public ResponseEntity<Counter> updateCounter(@Valid @RequestBody Counter counter) throws URISyntaxException {
        log.debug("REST request to update Counter : {}", counter);
        if (counter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Counter result = counterService.save(counter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, counter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /counters : get all the counters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of counters in body
     */
    @GetMapping("/counters")
    @Timed
    public List<Counter> getAllCounters() {
        log.debug("REST request to get all Counters");
        return counterService.findAll();
    }

    /**
     * GET  /counters/:id : get the "id" counter.
     *
     * @param id the id of the counter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the counter, or with status 404 (Not Found)
     */
    @GetMapping("/counters/{id}")
    @Timed
    public ResponseEntity<Counter> getCounter(@PathVariable Long id) {
        log.debug("REST request to get Counter : {}", id);
        Optional<Counter> counter = counterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(counter);
    }

    /**
     * DELETE  /counters/:id : delete the "id" counter.
     *
     * @param id the id of the counter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/counters/{id}")
    @Timed
    public ResponseEntity<Void> deleteCounter(@PathVariable Long id) {
        log.debug("REST request to delete Counter : {}", id);
        counterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
