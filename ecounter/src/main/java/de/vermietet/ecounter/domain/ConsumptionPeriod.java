package de.vermietet.ecounter.domain;

import java.time.temporal.TemporalUnit;

public class ConsumptionPeriod {
    private Long amount;
    private TemporalUnit unit;

    public ConsumptionPeriod(Long amount, TemporalUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public TemporalUnit getUnit() {
        return unit;
    }

    public void setUnit(TemporalUnit unit) {
        this.unit = unit;
    }
}
