package de.vermietet.ereport.service;

import de.vermietet.ereport.domain.Consumption;

import java.util.List;

public interface ConsumptionService {
    public List<Consumption> getConsumptions(String duration);
}
