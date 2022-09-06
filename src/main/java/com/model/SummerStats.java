package com.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;

public class SummerStats {

    private Summoner summoner;

    public SummerStats(Summoner summoner) {
        this.summoner = summoner;
    }

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        TypedQuery<Summoner> query = entityManager.createQuery("Select a from Summoner a where a.name = ?1", Summoner.class);
        query.setParameter(1, "AngryBacteria");
        List<Summoner> summoners = query.getResultList();

        transaction.commit();
        entityManager.close();
        emf.close();

        SummerStats summerStats = new SummerStats(summoners.get(0));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String carAsString = objectMapper.writeValueAsString(summerStats);
            System.out.println(carAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


    }

    public String getName(){
        return summoner.getName();
    }

    public String getPuuid(){
        return summoner.getPuuid();
    }

    public long getWins(){
        return summoner.getGames().values().stream().filter(Game::isWin).count();
    }

    public long getLosses(){
        return summoner.getGames().values().stream().filter(Predicate.not(Game::isWin)).count();
    }

    public int getTotalHealAndShield(){
        return summoner.getGames().values().stream().mapToInt(Game::getEffectiveHealAndShielding).sum();
    }

    public int getTotalBuffsStolen(){
        return summoner.getGames().values().stream().mapToInt(Game::getBuffsStolen).sum();
    }

    public int getTotalObjectivesStolen(){
        return summoner.getGames().values().stream().mapToInt(Game::getObjectivesStolen).sum();
    }

    public int getTotalDancedWithRiftHerald(){
        return summoner.getGames().values().stream().mapToInt(Game::getDancedWithRiftHerald).sum();
    }

    public int getTotalSkillshotsDodged(){
        return summoner.getGames().values().stream().mapToInt(Game::getSkillshotsDodged).sum();
    }

    public int getTotalSkillshotsHit(){
        return summoner.getGames().values().stream().mapToInt(Game::getSkillshotsHit).sum();
    }

    public int getTotalAbilityUses(){
        return summoner.getGames().values().stream().mapToInt(Game::getAbilityUses).sum();
    }

    public int getTotalDeaths(){
        return summoner.getGames().values().stream().mapToInt(Game::getDeaths).sum();
    }

    public int getTotalKills(){
        return summoner.getGames().values().stream().mapToInt(Game::getKills).sum();
    }

    public int getTotalAssists(){
        return summoner.getGames().values().stream().mapToInt(Game::getAssists).sum();
    }

    public int getTotalTurretKills(){
        return summoner.getGames().values().stream().mapToInt(Game::getTurretKills).sum();
    }

    public int getTotalMinionsKilled(){
        return summoner.getGames().values().stream().mapToInt(Game::getTotalMinionsKilled).sum();
    }

    public int getTotalDamageTaken(){
        return summoner.getGames().values().stream().mapToInt(Game::getTotalDamageTaken).sum();
    }

    public int getTotalDamageDealtToChampions(){
        return summoner.getGames().values().stream().mapToInt(Game::getTotalDamageDealtToChampions).sum();
    }

    public int getShortestGame(){
        return summoner.getGames().values().stream().mapToInt(Game::getGameDuration).min().getAsInt();
    }

    public int getLongestGame(){
        return summoner.getGames().values().stream().mapToInt(Game::getGameDuration).max().getAsInt();
    }

    public int getLargestCrit(){
        return summoner.getGames().values().stream().mapToInt(Game::getLargestCriticalStrike).max().getAsInt();
    }

    public int getLongestTimeCCingOthers(){
        return summoner.getGames().values().stream().mapToInt(Game::getTimeCCingOthers).max().getAsInt();
    }

    public int getLongestTimeDead(){
        return summoner.getGames().values().stream().mapToInt(Game::getTotalTimeSpentDead).max().getAsInt();
    }

    public int getTotalTimeDead(){
        return summoner.getGames().values().stream().mapToInt(Game::getTotalTimeSpentDead).sum();
    }


}
