package de.vermietet.ereport.service;

import de.vermietet.ereport.domain.EnergyConsumption;

import java.util.List;

public interface ReportService {
    public List<EnergyConsumption> getEnergyConsumptionByDuration(String duration);
}
