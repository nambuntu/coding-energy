package de.vermietet.ecounter.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Consumption.
 */
@Entity
@Table(name = "consumption")
public class Consumption implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "date_created", nullable = false)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @ManyToOne
    @JoinColumn(name = "counter_id", nullable = false)
    @JsonIgnoreProperties("")
    private Counter counter;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Consumption amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public ZonedDateTime getDateCreated() {
        return dateCreated;
    }

    public Consumption dateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(ZonedDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Counter getCounter() {
        return counter;
    }

    public Consumption counter(Counter counter) {
        this.counter = counter;
        return this;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Consumption consumption = (Consumption) o;
        if (consumption.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consumption.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consumption{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", dateCreated='" + getDateCreated() + "'" +
            "}";
    }
}
