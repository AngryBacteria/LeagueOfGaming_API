package angryb.model;

import angryb.model.timeline.TimeLine;
import no.stelar7.api.r4j.basic.APICredentials;
import no.stelar7.api.r4j.basic.constants.api.regions.LeagueShard;
import no.stelar7.api.r4j.basic.constants.api.regions.RegionShard;
import no.stelar7.api.r4j.basic.constants.types.lol.GameQueueType;
import no.stelar7.api.r4j.basic.constants.types.lol.MatchlistMatchType;
import no.stelar7.api.r4j.impl.R4J;
import no.stelar7.api.r4j.pojo.lol.match.v5.*;
import org.apache.commons.io.IOUtils;

import javax.persistence.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

/**
 * Helper class for the api controllers and some db update queries
 */
public class Helper {

    private Properties properties;
    private final APICredentials creds;
    private final R4J r4J;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";

    public static void main(String[] args) {

        Helper helper = new Helper();
        helper.addGamesToAllPlayers();
        helper.updateAllPlayers();
        helper.updateMetadata();
    }

    /**
     * Constructor. It reads the config.properties file to parse the RIOT API key needed for many functions
     */
    public Helper() {

        try (InputStream inputStream = Helper.class.getClassLoader().getResourceAsStream("config.properties")){

            this.properties = new Properties();
            properties.load(inputStream);
        }

        catch (IOException e) {
            System.out.println("Couldn't load up the config file correctly");
        }

        this.creds = new APICredentials(properties.getProperty("RIOT_API_KEY"));
        this.r4J = new R4J(this.creds);

    }

    /**
     * Add the last 95 games (ranked and normal queue) from the Summoner. This method uses the RIOT API
     * @param summoner Summoner object representing a League of Legends Account
     */
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
    }

    /**
     * Method that simply gets all players from the db and runs the addGamesToSummonerToDB on all of them
     */
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

    /**
     * Adds all players to the db. This method needs the RIOT API
     */
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

    /**
     * Searches a Player with the RIOT API, crates a summoner object and adds it to the db
     * @param playerName
     */
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

    /**
     * Method that updates the fields in the Summoner Objects that are subject to change (such as LvL, Name and ProfileIcon).
     * This method uses the RIOT API
     */
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

    /**
     * Method to specifically change the PUUID of all Summoner objects in the db.
     * This is only needed if the API KEY is chnged as the PUUID is decrypted with it.
     * This method uses the RIOT API
     */
    public void updatePUUIDS(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Summoner> summoners = entityManager
                .createQuery("Select a from Summoner a", Summoner.class)
                .getResultList();

        for (Summoner summoner : summoners){
            String currentPuuid = r4J.getLoLAPI().getSummonerAPI().getSummonerByName(LeagueShard.EUW1, summoner.getName()).getPUUID();
            if (!currentPuuid.equals(summoner.getPuuid())){
                System.out.println(ANSI_RED + "Changing summoner puuid from [" + summoner.getPuuid() +
                        "] To " + currentPuuid + ANSI_RESET);
                summoner.setPuuid(currentPuuid);
            }
        }
        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Adds Info (last updated) to the metadata table.
     */
    public void updateMetadata(){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        long summonerCount = (long)entityManager.createQuery("select count(s) from Summoner s").getSingleResult();
        long matchCount = (long)entityManager.createQuery("select count(g) from Game g").getSingleResult();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/LL/yyyy - HH:mm");
        Metadata metadata = new Metadata(LocalDateTime.now().format(formatter),
                summonerCount, matchCount);
        entityManager.persist(metadata);

        transaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Gets a Summoner Object from a Match Object.
     * This method uses the RIOT API indirectly
     * @param lolMatch Object representing a League of Legends Match (from the r4j Library)
     * @param summoner Object representing a League of Legends Summoner (My own Implementation)
     * @return Returns an Object representing a League of Legends Summoner (from the r4j Library)
     */
    public Optional<MatchParticipant> getParticipant(LOLMatch lolMatch, Summoner summoner){

        for (MatchParticipant matchParticipant : lolMatch.getParticipants()){
            if (matchParticipant.getPuuid().equals(summoner.getPuuid())){
                return Optional.of(matchParticipant);
            }
        }
        return Optional.empty();
    }

    /**
     * Gets the summoner icon URL with a Summoner Object as input.
     * This method uses the RIOT API
     * @param summoner Object representing a League of Legends Summoner (My own Implementation)
     * @return Returns the URL to the png of the summoner icon (profile Picture)
     */
    public String getSummonerIcon(Summoner summoner){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        String language = "en_US";
        int profileIconID = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, summoner.getPuuid()).getProfileIconId();
        return r4J.getImageAPI().getProfileIcon(String.valueOf(profileIconID), r4J.getDDragonAPI().getVersions().get(0));
    }

    /**
     * Gets the summoner icon URL with the PUUID as Input.
     * This method uses the RIOT API
     * @param puuid ID from the RIOT API which is decrypted with the RIOT API Key
     * @return Returns the URL to the png of the summoner icon (profile Picture)
     */
    public String getSummonerIcon(String puuid){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        String language = "en_US";
        int profileIconID = r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, puuid).getProfileIconId();
        return r4J.getImageAPI().getProfileIcon(String.valueOf(profileIconID), r4J.getDDragonAPI().getVersions().get(0));
    }

    /**
     * Gets the summoner lvl from the riot api with a Summoner object as Input.
     * This method uses the RIOT API
     * @param summoner Object representing a League of Legends Summoner (My own Implementation)
     * @return Summoner lvl as an Integer
     */
    public Integer getSummonerLvL(Summoner summoner){

        return r4J.getLoLAPI().getSummonerAPI().getSummonerByPUUID(LeagueShard.EUW1, summoner.getPuuid()).getSummonerLevel();
    }

    /**
     * Gets the Icon URL of a League of Legends Champion
     * @param championName Name of a League of Legends Champion
     * @return URL to a png of the League of Legends champion icon
     */
    public String getChampionPicture(String championName){

        String latestVersion = r4J.getDDragonAPI().getVersions().get(0);
        return "https://ddragon.leagueoflegends.com/cdn/"+latestVersion+"/img/champion/"+championName+".png";
    }

    /**
     * Gets a Json String from a URL
     * @param url String representing a URL
     * @return Returns a String representing a Json
     */
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

    /**
     * Gets the TimeLine (Match info with timestamps) from a GameID String
     * @param gameId GameID String
     * @return TimeLine Object (from the r4j Library)
     */
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

    /**
     * Method which returns the Credentials Object (from the r4j Library)
     * @return APICredentials Object (from the r4j Library)
     */
    public APICredentials getCreds() {
        return creds;
    }

    /**
     * Method which returns the r4j Object which is needed for API calls to the RIOT API
     * @return R4J Object
     */
    public R4J getR4J() {
        return r4J;
    }

    public Properties getProperties() {
        return properties;
    }
}
