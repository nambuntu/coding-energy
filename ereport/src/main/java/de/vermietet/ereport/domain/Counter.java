package de.vermietet.ereport.domain;

public class Counter {
    private Long id;
    private String villageName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    @Override
    public boolean equals(Object obj) {
        Counter compare = (Counter) obj;

        return this.id == compare.getId();
    }
}
