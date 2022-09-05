package com.controllers;
import com.model.Summoner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;

@RestController
public class SummonerController {

    @GetMapping("/api/summoner/{name}")
    @ResponseBody
    public Summoner summonerName(@PathVariable String name) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where a.name = ?1", Summoner.class);
        query.setParameter(1, name);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return summoners.get(0);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner could not be found");
    }

    @GetMapping("/api/summoner/{puuid}")
    @ResponseBody
    public Summoner summonerPUUID(@PathVariable String puuid) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where a.puuid = ?1", Summoner.class);
        query.setParameter(1, puuid);
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        if (summoners.size() > 0)
            return summoners.get(0);
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Summoner could not be found");
    }
}
