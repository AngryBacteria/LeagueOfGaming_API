package angryb.old.model;

import javax.persistence.*;

@Entity
public class Metadata {

    private String lastUpdated;
    private long numberOfSummonera;
    private long numberOfMatches;
    @Id
    @GeneratedValue(generator = "METADATA_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "METADATA_SEQ", sequenceName = "METADATA_SEQ",allocationSize=1)
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

    @Id
    public Long getId() {
        return id;
    }
}
