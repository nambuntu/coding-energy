package de.vermietet.ereport.domain;

public class Consumption {
    private Long id;
    private Double amount;
    private Counter counter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Counter getCounter() {
        return counter;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s", id, amount, counter.getVillageName());
    }
}
