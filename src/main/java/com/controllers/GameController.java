package com.controllers;

import com.model.Game;
import com.model.Summoner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {


    @GetMapping("/")
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

}
