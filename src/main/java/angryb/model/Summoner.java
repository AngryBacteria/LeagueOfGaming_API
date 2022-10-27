package angryb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Summoner {

    @Id
    @SequenceGenerator(name = "summonerSequence", sequenceName = "summonerSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "summonerSequence")
    private Long id;
    private String name;
    @Column(unique=true)
    private String puuid;

    @OneToMany(mappedBy = "summoner", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JsonIgnore
    private Map<String, Game> games;

    private String pictureURL;
    private Integer summonerLvL;

    public Summoner(String name, String puuid) {
        this.name = name;
        this.puuid = puuid;
        this.games = new HashMap<>();
    }

    public Summoner() {

    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void addGame(Game game){
        if (games.containsKey(game.getGameURL())) {
            System.out.println("Game already in object");
            return;
        }
        this.games.put(game.getGameURL(), game);
    }

    public String getName() {
        return name;
    }

    public String getPuuid() {
        return puuid;
    }

    public void printGames(){
        this.games.values().forEach(System.out::println);
    }

    @JsonIgnore
    public Map<String, Game> getGames() {
        return games;
    }

    public List<Game> getGamesList() {
        return games.values().stream().sorted(Comparator.comparing(Game::getGameEnd).reversed()).toList();
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSummonerLvL(Integer summonerLvL) {
        this.summonerLvL = summonerLvL;
    }

    public Integer getSummonerLvL() {
        return summonerLvL;
    }

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }

    @Override
    public String toString() {
        return "Summoner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", puuid='" + puuid + '\'' +
                ", games=" + games +
                '}';
    }
}