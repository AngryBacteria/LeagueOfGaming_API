package com.model;

import javax.persistence.*;

@Entity
public class Metadata {

    private String lastUpdated;
    @Id
    @GeneratedValue(generator = "METADATA_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "METADATA_SEQ", sequenceName = "METADATA_SEQ",allocationSize=1)
    private Long id;

    public Metadata(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Metadata() {}

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
