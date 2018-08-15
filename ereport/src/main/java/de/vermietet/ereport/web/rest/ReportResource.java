package de.vermietet.ereport.web.rest;

import de.vermietet.ereport.domain.EnergyConsumption;
import de.vermietet.ereport.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ReportResource controller
 */
@RestController
@RequestMapping("/api")
public class ReportResource {

    private final Logger log = LoggerFactory.getLogger(ReportResource.class);
    @Autowired
    private ReportService reportService;

    /**
     * GET getReport
     */
    @GetMapping("/consumption_report")
    public List<EnergyConsumption> getReport(@RequestParam("duration") String duration) {
        return reportService.getEnergyConsumptionByDuration(duration);
    }

}
