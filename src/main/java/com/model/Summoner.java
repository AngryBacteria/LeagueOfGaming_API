package com.model;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Summoner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique=true)
    private String puuid;


    @OneToMany(mappedBy = "summoner", cascade = CascadeType.PERSIST)
    private Map<String, Game> games;


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

    public Map<String, Game> getGames() {
        return games;
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
