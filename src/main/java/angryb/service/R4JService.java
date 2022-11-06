package angryb.service;

import angryb.model.Game;
import angryb.model.Summoner;
import angryb.model.timeline.TimeLine;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.MatchlistMatchType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
@Service("Init")
public class R4JService {

    private final APICredentials creds;
    private final R4J r4J;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {

    }

    @Autowired
    public R4JService() {
        this.creds = new APICredentials("RGAPI-c11f6576-d7d3-4409-b0f4-10cb00b7c70e");
        this.r4J = new R4J(this.creds);
    }

    public void addGamesToSummonerToDB(Summoner summoner, SummonerService summonerService){

        List<String> matchListNormal = r4J.getLoLAPI().getMatchAPI().getMatchList(RegionShard.EUROPE, summoner.getPuuid(),
                GameQueueType.TEAM_BUILDER_DRAFT_UNRANKED_5X5, MatchlistMatchType.NORMAL, 480, 90,
                1651619730L, Instant.now().getEpochSecond());

        List<String> matchListRanked = r4J.getLoLAPI().getMatchAPI().getMatchList(RegionShard.EUROPE, summoner.getPuuid(),
                GameQueueType.TEAM_BUILDER_RANKED_SOLO, MatchlistMatchType.RANKED, 480, 90,
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
                game.setAbilityUses(((Double)participant.getChallenges().get("abilityUses")).intValue());
                game.setBountyGold(((Double)participant.getChallenges().get("bountyGold")).intValue());
                game.setBuffsStolen(((Double)participant.getChallenges().get("buffsStolen")).intValue());
                game.setDancedWithRiftHerald(((Double)participant.getChallenges().get("dancedWithRiftHerald")).intValue());
                //game.setEarliestDragonTakedown(((Double)participant.getChallenges().get("earliestDragonTakedown")).intValue());
                game.setEffectiveHealAndShielding(((Double)participant.getChallenges().get("effectiveHealAndShielding")).intValue());
                //game.setFirstTurretKilledTime(((Double)participant.getChallenges().get("firstTurretKilledTime")).intValue());
                game.setHadOpenNexus(((Double)participant.getChallenges().get("hadOpenNexus")).intValue());
                game.setMultiKillOneSpell(((Double)participant.getChallenges().get("multiKillOneSpell")).intValue());
                game.setSkillshotsDodged(((Double)participant.getChallenges().get("skillshotsDodged")).intValue());
                game.setSkillshotsHit(((Double)participant.getChallenges().get("skillshotsHit")).intValue());
                game.setSurvivedSingleDigitHpCount(((Double)participant.getChallenges().get("survivedSingleDigitHpCount")).intValue());
                game.setTeamDamagePercentage(((Double)participant.getChallenges().get("teamDamagePercentage")));

                //Basic fields
                game.setAssists(participant.getAssists());
                game.setChampionID(participant.getChampionId());
                game.setChampionName(participant.getChampionName());
                game.setDeaths(participant.getDeaths());
                game.setDoubleKills(participant.getDoubleKills());
                game.setFirstBloodKill(participant.isFirstBloodKill());
                game.setGameDuration(match.getGameDuration());
                game.setGameEnd(match.getGameEndAsDate().toLocalDateTime());
                game.setKillingSprees(participant.getKillingSprees());
                game.setKills(participant.getKills());
                game.setLargestCriticalStrike(participant.getLargestCriticalStrike());
                game.setLargestKillingSpree(participant.getLargestKillingSpree());
                game.setLargestMultiKill(participant.getLargestMultiKill());
                game.setNeutralMinionsKilled(participant.getNeutralMinionsKilled());
//                game.setNumberOfPings(participant.getNumberOfPings);
                game.setObjectivesStolen(participant.getObjectivesStolen());
                game.setObjectivesStolenAssists(participant.getObjectivesStolenAssists());
                game.setQuadraKills(participant.getQuadraKills());
                game.setTeamPosition(participant.getGameDeterminedPosition().getValue());
                game.setTimeCCingOthers(participant.getTimeCCingOthers());
                game.setTotalDamageDealt(participant.getTotalDamageDealt());
                game.setTotalDamageDealtToChampions(participant.getTotalDamageDealtToChampions());
                game.setTotalDamageTaken(participant.getTotalDamageTaken());
                game.setTotalMinionsKilled(participant.getTotalMinionsKilled());
                game.setTotalTimeSpentDead(participant.getTotalTimeSpentDead());
                game.setTripleKills(participant.getTripleKills());
                game.setTurretKills(participant.getTurretKills());
                game.setVisionScore(participant.getVisionScore());
                game.setWin(participant.didWin());

                summoner.addGame(game);
                System.out.println(ANSI_GREEN + "Game [" + game.getGameURL() + "] added to summoner " + summoner.getName() + ANSI_RESET);
            }
        }
        summonerService.persistSummoner(summoner);
    }

    public void addTestGameToSummoner(Summoner summoner, SummonerService summonerService){

        Game game = new Game(summoner, "megacoolgametest123");
        summoner.addGame(game);
        summonerService.persistSummoner(summoner);
    }

    public Optional<MatchParticipant> getParticipant(LOLMatch lolMatch, Summoner summoner){

        for (MatchParticipant matchParticipant : lolMatch.getParticipants()){
            if (matchParticipant.getPuuid().equals(summoner.getPuuid())){
                return Optional.of(matchParticipant);
            }
        }
        return Optional.empty();
    }

    public List<Summoner> createAllSummoners(){

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

        List<Summoner> summoners = new ArrayList<>();
        for (String playerName : players){

            no.stelar7.api.r4j.pojo.lol.summoner.Summoner summonerApiObject = r4J.getLoLAPI().getSummonerAPI().getSummonerByName(LeagueShard.EUW1, playerName);
            Summoner summoner = new Summoner(playerName, summonerApiObject.getPUUID());
            summoner.setSummonerLvL(summonerApiObject.getSummonerLevel());
            summoner.setPictureURL(this.getSummonerIcon(summoner.getPuuid()));
            summoners.add(summoner);
        }
        return summoners;
    }

    public String getJsonFromUrl(String url){

        String json = null;
        try {
            URL urlObject = new URL(url);
            json = IOUtils.toString(urlObject, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public APICredentials getCreds() {
        return creds;
    }

    public R4J getR4J() {
        return r4J;
    }

    public TimeLine getTimeLineFromGame(String gameId){

        LOLTimeline lolTimeline = this.getR4J().getLoLAPI().getMatchAPI().getTimeline(RegionShard.EUROPE, gameId);
        TimeLine timeLine = new TimeLine();

        for (TimelineParticipantIdentity participantIdentity : lolTimeline.getParticipants()){
            timeLine.addPlayer(participantIdentity.getPuuid());
        }

        int index = 0;
        for (TimelineFrame frame : lolTimeline.getFrames()){

            int team1Total = 0;
            int team2Total = 0;
            for (TimelineParticipantFrame participantFrame : frame.getParticipantFrames().values()){

                if (participantFrame.getParticipantId() < 6)
                    team1Total = team1Total + participantFrame.getTotalGold();
                else
                    team2Total = team2Total + participantFrame.getTotalGold();
            }
            timeLine.addTeam1Gold(team1Total, index);
            timeLine.addTeam2Gold(team2Total, index);
            index++;
        }
        return timeLine;
    }

    public String getSummonerIcon(String puuid){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        String language = "en_US";
        int profileIconID = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, puuid).getProfileIconId();
        return r4J.getImageAPI().getProfileIcon(String.valueOf(profileIconID), r4J.getDDragonAPI().getVersions().get(0));
    }
}
