package de.vermietet.ereport.service.impl;

import de.vermietet.ereport.domain.Consumption;
import de.vermietet.ereport.domain.EnergyConsumption;
import de.vermietet.ereport.service.ConsumptionService;
import de.vermietet.ereport.service.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ConsumptionService consumptionService;

    private final Logger log = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public List<EnergyConsumption> getEnergyConsumptionByDuration(String duration) {
        List<Consumption> consumptions = consumptionService.getConsumptions(duration);
        Map<String, Double> counterConsumption = new HashMap<>();

        Double subTotal;
        for (Consumption consumption : consumptions) {
            String villageName = consumption.getCounter().getVillageName();
            if (counterConsumption.get(villageName) != null) {
                subTotal = counterConsumption.get(villageName);
                counterConsumption.put(villageName, subTotal + consumption.getAmount());
            } else {
                counterConsumption.put(villageName, consumption.getAmount());
            }
        }

        List<EnergyConsumption> energyConsumptions = counterConsumption
            .entrySet()
            .stream().map(e -> new EnergyConsumption(e.getKey(), e.getValue()))
            .collect(Collectors.toList());

        return energyConsumptions;

    }
}
