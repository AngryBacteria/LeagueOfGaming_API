package com.controllers;

import com.model.Game;
import com.model.Helper;
import com.model.SummerStats;
import com.model.Summoner;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {


    @GetMapping("/db/")
    @ResponseBody
    public List<Game> allGames() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Game> games = entityManager
                .createQuery("Select g from Game g", Game.class)
                .getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        return games;
    }

    @GetMapping("/db/gameurl/{gameurl}")
    @ResponseBody
    public Game gameByURL(@PathVariable String gameurl) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Game> query = entityManager.createQuery("Select g from Game g where g.gameURL = ?1", Game.class);
        query.setParameter(1, gameurl);
        List<Game> games = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (games.size() > 0)
            return games.get(0);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game could not be found");
    }

    @GetMapping("/riot/gameid/{gameid}")
    @ResponseBody
    public String gameFromRiot(@PathVariable String gameid) {

        Helper helper = new Helper();
        return helper.getJsonFromUrl(String.format
                ("https://europe.api.riotgames.com/lol/match/v5/matches/%s?api_key=%s",
                        gameid, helper.getCreds().getLoLAPIKey()));
    }

}
