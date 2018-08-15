package de.vermietet.ereport.domain;

public class EnergyConsumption {
    private String villageName;
    private Double consumption;

    public EnergyConsumption(String villageName, Double consumption) {
        this.villageName = villageName;
        this.consumption = consumption;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Double getConsumption() {
        return consumption;
    }

    public void setConsumption(Double consumption) {
        this.consumption = consumption;
    }
}
