package angryb.model;

import javax.persistence.*;

@Entity
public class Metadata {

    private String lastUpdated;
    private long numberOfSummonera;
    private long numberOfMatches;
    @Id
    @SequenceGenerator(name = "metadataSequence", sequenceName = "metadataSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metadataSequence")
    private Long id;

    public Metadata(String lastUpdated, long numberOfSummoners, long numberOfMatches) {
        this.lastUpdated = lastUpdated;
        this.numberOfSummonera = numberOfSummoners;
        this.numberOfMatches = numberOfMatches;
    }

    public Metadata() {}

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getNumberOfSummonera() {
        return numberOfSummonera;
    }

    public void setNumberOfSummonera(long numberOfSummonera) {
        this.numberOfSummonera = numberOfSummonera;
    }

    public long getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(long numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}