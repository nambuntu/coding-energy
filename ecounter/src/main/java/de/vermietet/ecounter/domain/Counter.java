package de.vermietet.ecounter.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Counter.
 */
@Entity
@Table(name = "counter")
public class Counter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "village_name", length = 250, nullable = false)
    private String villageName;

    @OneToMany(mappedBy = "counter")
    private Set<Consumption> consumptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVillageName() {
        return villageName;
    }

    public Counter villageName(String villageName) {
        this.villageName = villageName;
        return this;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public Set<Consumption> getConsumptions() {
        return consumptions;
    }

    public Counter consumptions(Set<Consumption> consumptions) {
        this.consumptions = consumptions;
        return this;
    }

    public Counter addConsumption(Consumption consumption) {
        this.consumptions.add(consumption);
        consumption.setCounter(this);
        return this;
    }

    public Counter removeConsumption(Consumption consumption) {
        this.consumptions.remove(consumption);
        consumption.setCounter(null);
        return this;
    }

    public void setConsumptions(Set<Consumption> consumptions) {
        this.consumptions = consumptions;
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
        Counter counter = (Counter) o;
        if (counter.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), counter.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Counter{" +
            "id=" + getId() +
            ", villageName='" + getVillageName() + "'" +
            "}";
    }
}
