package com.model;

import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.MatchlistMatchType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.LOLMatch;
import no.stelar7.api.r4j.pojo.lol.match.v5.MatchParticipant;
import org.postgresql.jdbc.PgConnection;

import javax.persistence.*;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class Helper {

    public static APICredentials creds = new APICredentials("RGAPI-2d92b285-edec-4043-91be-77ffb4527da9");
    public static R4J r4J = new R4J(creds);

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {

        Helper helper = new Helper();
        helper.addGamesToAllPlayers();

    }

    public Helper() {}

    public void addGamesToSummonerToDB(Summoner summoner){

        List<String> matchListNormal = r4J.getLoLAPI().getMatchAPI().getMatchList(RegionShard.EUROPE, summoner.getPuuid(),
                GameQueueType.TEAM_BUILDER_DRAFT_UNRANKED_5X5, MatchlistMatchType.NORMAL, 0, 95,
                1651619730L, Instant.now().getEpochSecond());

        List<String> matchListRanked = r4J.getLoLAPI().getMatchAPI().getMatchList(RegionShard.EUROPE, summoner.getPuuid(),
                GameQueueType.TEAM_BUILDER_RANKED_SOLO, MatchlistMatchType.RANKED, 0, 95,
                1651619730L, Instant.now().getEpochSecond());

        List<String> newList = Stream.concat(matchListNormal.stream(), matchListRanked.stream()).toList();


        for (String matchString : newList){

            if (summoner.getGames().containsKey(matchString)) {
                System.out.println(ANSI_BLUE +"Game [" + matchString + "] already exists in db for player [" + summoner.getName() +"]"+ ANSI_RESET);
            }
            else {

                LOLMatch match = r4J.getLoLAPI().getMatchAPI().getMatch(RegionShard.EUROPE, matchString);
                MatchParticipant participant = getParticipant(match, summoner).get();
                Game game = new Game(summoner, matchString);
                game.setQueueType(match.getQueue().getApiName());

                //Challenges
                game.setEffectiveHealAndShielding(((Double)participant.getChallenges().get("effectiveHealAndShielding")).intValue());
                game.setBountyGold(((Double)participant.getChallenges().get("bountyGold")).intValue());
                //game.setEarliestDragonTakedown(((Double)participant.getChallenges().get("earliestDragonTakedown")).intValue());
                //game.setFirstTurretKilledTime(((Double)participant.getChallenges().get("firstTurretKilledTime")).intValue());
                game.setHadOpenNexus(((Double)participant.getChallenges().get("hadOpenNexus")).intValue());
                game.setMultiKillOneSpell(((Double)participant.getChallenges().get("multiKillOneSpell")).intValue());
                game.setSurvivedSingleDigitHpCount(((Double)participant.getChallenges().get("survivedSingleDigitHpCount")).intValue());
                game.setTeamDamagePercentage(((Double)participant.getChallenges().get("teamDamagePercentage")));
                game.setBuffsStolen(((Double)participant.getChallenges().get("buffsStolen")).intValue());
                game.setDancedWithRiftHerald(((Double)participant.getChallenges().get("dancedWithRiftHerald")).intValue());
                game.setSkillshotsDodged(((Double)participant.getChallenges().get("skillshotsDodged")).intValue());
                game.setSkillshotsHit(((Double)participant.getChallenges().get("skillshotsHit")).intValue());
                game.setAbilityUses(((Double)participant.getChallenges().get("abilityUses")).intValue());

                //Basic fields
                game.setChampionID(participant.getChampionId());
                game.setDoubleKills(participant.getDoubleKills());
                game.setFirstBloodKill(participant.isFirstBloodKill());
                game.setKillingSprees(participant.getKillingSprees());
                game.setNeutralMinionsKilled(participant.getNeutralMinionsKilled());
                game.setObjectivesStolen(participant.getObjectivesStolen());
                game.setObjectivesStolenAssists(participant.getObjectivesStolenAssists());
                game.setQuadraKills(participant.getQuadraKills());
                game.setTotalDamageDealt(participant.getTotalDamageDealt());
                game.setTotalDamageDealtToChampions(participant.getTotalDamageDealtToChampions());
                game.setTotalDamageTaken(participant.getTotalDamageTaken());
                game.setTotalMinionsKilled(participant.getTotalMinionsKilled());
                game.setTripleKills(participant.getTripleKills());
                game.setTurretKills(participant.getTurretKills());
                game.setVisionScore(participant.getVisionScore());
                game.setDeaths(participant.getDeaths());
                game.setKills(participant.getKills());
                game.setAssists(participant.getAssists());
                game.setGameDuration(match.getGameDuration());
                game.setChampionName(participant.getChampionName());
                game.setLargestCriticalStrike(participant.getLargestCriticalStrike());
                game.setLargestKillingSpree(participant.getLargestKillingSpree());
                game.setLargestMultiKill(participant.getLargestMultiKill());
                game.setTeamPosition(participant.getGameDeterminedPosition().getValue());
                game.setTimeCCingOthers(participant.getTimeCCingOthers());
                game.setTotalTimeSpentDead(participant.getTotalTimeSpentDead());
                game.setWin(participant.didWin());
                game.setGameEnd(match.getGameEndAsDate().toLocalDateTime());

                summoner.addGame(game);

                System.out.println(ANSI_GREEN + "Game [" + game.getGameURL() + "] added to summoner " + summoner.getName() + ANSI_RESET);
            }
        }
    }

    public void addGamesToAllPlayers(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Summoner> summoners = entityManager
                .createQuery("Select a from Summoner a", Summoner.class)
                .getResultList();

        for (Summoner summoner : summoners){
            this.addGamesToSummonerToDB(summoner);
        }

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void addAllPlayersToDB(){

        ArrayList<String> players = new ArrayList<>();
        //Cap
        players.add("AngryBacteria");
        players.add("WhatThePlay");
        players.add("Alraune22");
        players.add("ELU Tarnished");
        players.add("Prequ");
        players.add("Bay Butcher");
        players.add("GLOMVE");
        players.add("pentaskill");
        players.add("Árexo");

        //best server
        players.add("Gnerfedurf");
        players.add("nolsterpolster");
        players.add("hide on büschli");
        players.add("Lonely Toplaner");
        players.add("tresserhorn");
        players.add("viranyx");
        players.add("UnifixingGoblin5");
        players.add("Theera3rd");


        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();
        for (String playerName : players){

            Summoner summoner = new Summoner(playerName, r4J.getLoLAPI().getSummonerAPI().getSummonerByName(LeagueShard.EUW1, playerName).getPUUID());
            entityManager.persist(summoner);
        }
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void addPlayerToDB(String playerName){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Summoner summoner = new Summoner(playerName, r4J.getLoLAPI().getSummonerAPI().getSummonerByName(LeagueShard.EUW1, playerName).getPUUID());
        entityManager.persist(summoner);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    public void updateAllPlayers(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Summoner> summoners = entityManager
                .createQuery("Select a from Summoner a", Summoner.class)
                .getResultList();

        for (Summoner summoner : summoners){

            String apiName = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, summoner.getPuuid()).getName();
            if (!apiName.equals(summoner.getName())){
                System.out.println(ANSI_RED + "Changing summoner name from [" + summoner.getName() +
                        "] To " + apiName + ANSI_RESET);
                summoner.setName(apiName);
            }

            String apiPictureURL = this.getSummonerIcon(summoner);
            if (!apiPictureURL.equals(summoner.getPictureURL())){
                System.out.println(ANSI_RED + "Changing summoner icon from [" + summoner.getPictureURL() +
                        "] To " + apiPictureURL + ANSI_RESET);
                summoner.setPictureURL(apiPictureURL);
            }

            Integer apiSummonerLvL = this.getSummonerLvL(summoner);
            if (!Objects.equals(apiSummonerLvL, summoner.getSummonerLvL())){
                System.out.println(ANSI_RED + "Changing summoner lvl from [" + summoner.getSummonerLvL() +
                        "] To " + apiSummonerLvL + ANSI_RESET);
                summoner.setSummonerLvL(apiSummonerLvL);
            }
        }

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();


    }

    public Optional<MatchParticipant> getParticipant(LOLMatch lolMatch, Summoner summoner){

        for (MatchParticipant matchParticipant : lolMatch.getParticipants()){
            if (matchParticipant.getPuuid().equals(summoner.getPuuid())){
                return Optional.of(matchParticipant);
            }
        }
        return Optional.empty();
    }

    public String getSummonerIcon(Summoner summoner){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        String language = "en_US";
        int profileIconID = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, summoner.getPuuid()).getProfileIconId();
        return r4J.getImageAPI().getProfileIcon(String.valueOf(profileIconID), r4J.getDDragonAPI().getVersions().get(0));
    }

    public String getSummonerIcon(String puuid){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        String language = "en_US";
        int profileIconID = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, puuid).getProfileIconId();
        return r4J.getImageAPI().getProfileIcon(String.valueOf(profileIconID), r4J.getDDragonAPI().getVersions().get(0));
    }

    public Integer getSummonerLvL(Summoner summoner){

        return r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, summoner.getPuuid()).getSummonerLevel();
    }

    public String getChampionPicture(String championName){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        return "https://ddragon.leagueoflegends.com/cdn/"+latestVersion+"/img/champion/"+championName+".png";
    }
}
