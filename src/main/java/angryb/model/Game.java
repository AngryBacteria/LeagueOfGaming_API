package angryb.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @SequenceGenerator(name = "gameSequence", sequenceName = "gameSequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameSequence")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Summoner summoner;
    private long summonerID;
    private String gameURL;
    private String queueType;

    //Challenges
    private Integer abilityUses;
    private Integer bountyGold;
    private Integer buffsStolen;
    private Integer dancedWithRiftHerald;
    //private int earliestDragonTakedown;
    private Integer effectiveHealAndShielding;
    //private int firstTurretKilledTime;
    private Integer hadOpenNexus;
    private Integer multiKillOneSpell;
    private Integer skillshotsDodged;
    private Integer skillshotsHit;
    private Integer survivedSingleDigitHpCount;
    private double teamDamagePercentage;

    //Normal stats
    private Integer assists;
    private Integer championID;
    private String championName;
    private Integer deaths;
    private Integer doubleKills;
    private boolean firstBloodKill;
    private Integer gameDuration;
    private LocalDateTime gameEnd;
    private Integer killingSprees;
    private Integer kills;
    private Integer largestCriticalStrike;
    private Integer largestKillingSpree;
    private Integer largestMultiKill;
    private Integer neutralMinionsKilled;
    private int numberOfPings;
    private Integer objectivesStolen;
    private Integer objectivesStolenAssists;
    private Integer quadraKills;
    private String teamPosition;
    private Integer timeCCingOthers;
    private Integer totalDamageDealt;
    private Integer totalDamageDealtToChampions;
    private Integer totalDamageTaken;
    private Integer totalMinionsKilled;
    private Integer totalTimeSpentDead;
    private Integer tripleKills;
    private Integer turretKills;
    private Integer visionScore;
    private boolean win;

    public Game(Summoner summoner, String gameURL) {
        this.summoner = summoner;
        this.gameURL = gameURL;
    }

    public Game() {
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public Summoner getSummoner() {
        return summoner;
    }

    public String getGameURL() {
        return gameURL;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public int getNumberOfPings() {
        return numberOfPings;
    }

    public void setNumberOfPings(int numberOfPings) {
        this.numberOfPings = numberOfPings;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(int duration) {
        this.gameDuration = duration;
    }

    public int getBuffsStolen() {
        return buffsStolen;
    }

    public void setBuffsStolen(int buffsStolen) {
        this.buffsStolen = buffsStolen;
    }

    public int getAbilityUses() {
        return abilityUses;
    }

    public void setAbilityUses(int abilityUses) {
        this.abilityUses = abilityUses;
    }

    public int getDancedWithRiftHerald() {
        return dancedWithRiftHerald;
    }

    public void setDancedWithRiftHerald(int dancedWithRiftHerald) {
        this.dancedWithRiftHerald = dancedWithRiftHerald;
    }

    public int getSkillshotsDodged() {
        return skillshotsDodged;
    }

    public void setSkillshotsDodged(int skillshotsDodged) {
        this.skillshotsDodged = skillshotsDodged;
    }

    public int getSkillshotsHit() {
        return skillshotsHit;
    }

    public void setSkillshotsHit(int skillshotsHit) {
        this.skillshotsHit = skillshotsHit;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public int getLargestCriticalStrike() {
        return largestCriticalStrike;
    }

    public void setLargestCriticalStrike(int largestCriticalStrike) {
        this.largestCriticalStrike = largestCriticalStrike;
    }

    public int getLargestKillingSpree() {
        return largestKillingSpree;
    }

    public void setLargestKillingSpree(int largestKillingSpree) {
        this.largestKillingSpree = largestKillingSpree;
    }

    public int getLargestMultiKill() {
        return largestMultiKill;
    }

    public void setLargestMultiKill(int largestMultiKill) {
        this.largestMultiKill = largestMultiKill;
    }

    public int getTimeCCingOthers() {
        return timeCCingOthers;
    }

    public void setTimeCCingOthers(int timeCCingOthers) {
        this.timeCCingOthers = timeCCingOthers;
    }

    public int getTotalTimeSpentDead() {
        return totalTimeSpentDead;
    }

    public void setTotalTimeSpentDead(int totalTimeSpentDead) {
        this.totalTimeSpentDead = totalTimeSpentDead;
    }

    public boolean isWin() {
        return win;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }

    public void setWin(boolean win) {
        this.win = win;
    }

    public LocalDateTime getGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(LocalDateTime gameEnd) {
        this.gameEnd = gameEnd;
    }

    public int getEffectiveHealAndShielding() {
        return effectiveHealAndShielding;
    }

    public void setEffectiveHealAndShielding(int effectiveHealAndShielding) {
        this.effectiveHealAndShielding = effectiveHealAndShielding;
    }

    public int getBountyGold() {
        return bountyGold;
    }

    public void setBountyGold(int bountyGold) {
        this.bountyGold = bountyGold;
    }

    public int getHadOpenNexus() {
        return hadOpenNexus;
    }

    public void setHadOpenNexus(int hadOpenNexus) {
        this.hadOpenNexus = hadOpenNexus;
    }

    public int getMultiKillOneSpell() {
        return multiKillOneSpell;
    }

    public void setMultiKillOneSpell(int multiKillOneSpell) {
        this.multiKillOneSpell = multiKillOneSpell;
    }

    public int getSurvivedSingleDigitHpCount() {
        return survivedSingleDigitHpCount;
    }

    public void setSurvivedSingleDigitHpCount(int survivedSingleDigitHpCount) {
        this.survivedSingleDigitHpCount = survivedSingleDigitHpCount;
    }

    public double getTeamDamagePercentage() {
        return teamDamagePercentage;
    }

    public void setTeamDamagePercentage(double teamDamagePercentage) {
        this.teamDamagePercentage = teamDamagePercentage;
    }

    public int getChampionID() {
        return championID;
    }

    public void setChampionID(int championID) {
        this.championID = championID;
    }

    public int getDoubleKills() {
        return doubleKills;
    }

    public void setDoubleKills(int doubleKills) {
        this.doubleKills = doubleKills;
    }

    public boolean isFirstBloodKill() {
        return firstBloodKill;
    }

    public void setFirstBloodKill(boolean firstBloodKill) {
        this.firstBloodKill = firstBloodKill;
    }

    public int getKillingSprees() {
        return killingSprees;
    }

    public void setKillingSprees(int killingSprees) {
        this.killingSprees = killingSprees;
    }

    public int getNeutralMinionsKilled() {
        return neutralMinionsKilled;
    }

    public void setNeutralMinionsKilled(int neutralMinionsKilled) {
        this.neutralMinionsKilled = neutralMinionsKilled;
    }

    public int getObjectivesStolen() {
        return objectivesStolen;
    }

    public void setObjectivesStolen(int objectivesStolen) {
        this.objectivesStolen = objectivesStolen;
    }

    public int getObjectivesStolenAssists() {
        return objectivesStolenAssists;
    }

    public void setObjectivesStolenAssists(int objectivesStolenAssists) {
        this.objectivesStolenAssists = objectivesStolenAssists;
    }

    public int getQuadraKills() {
        return quadraKills;
    }

    public void setQuadraKills(int quadraKills) {
        this.quadraKills = quadraKills;
    }

    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(int totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public int getTotalDamageDealtToChampions() {
        return totalDamageDealtToChampions;
    }

    public void setTotalDamageDealtToChampions(int totalDamageDealtToChampions) {
        this.totalDamageDealtToChampions = totalDamageDealtToChampions;
    }

    public int getTotalDamageTaken() {
        return totalDamageTaken;
    }

    public void setTotalDamageTaken(int totalDamageTaken) {
        this.totalDamageTaken = totalDamageTaken;
    }

    public int getTotalMinionsKilled() {
        return totalMinionsKilled;
    }

    public void setTotalMinionsKilled(int totalMinionsKilled) {
        this.totalMinionsKilled = totalMinionsKilled;
    }

    public int getTripleKills() {
        return tripleKills;
    }

    public void setTripleKills(int tripleKills) {
        this.tripleKills = tripleKills;
    }

    public int getTurretKills() {
        return turretKills;
    }

    public void setTurretKills(int turretKills) {
        this.turretKills = turretKills;
    }

    public int getVisionScore() {
        return visionScore;
    }

    public void setVisionScore(int visionScore) {
        this.visionScore = visionScore;
    }

    public long getSummonerID() {
        return this.summoner.getId();
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", gameURL='" + gameURL + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return buffsStolen == game.buffsStolen && dancedWithRiftHerald == game.dancedWithRiftHerald && skillshotsDodged == game.skillshotsDodged && skillshotsHit == game.skillshotsHit && abilityUses == game.abilityUses && numberOfPings == game.numberOfPings && deaths == game.deaths && kills == game.kills && assists == game.assists && gameDuration == game.gameDuration && largestCriticalStrike == game.largestCriticalStrike && largestKillingSpree == game.largestKillingSpree && largestMultiKill == game.largestMultiKill && timeCCingOthers == game.timeCCingOthers && totalTimeSpentDead == game.totalTimeSpentDead && win == game.win && Objects.equals(id, game.id) && Objects.equals(summoner, game.summoner) && Objects.equals(gameURL, game.gameURL) && Objects.equals(championName, game.championName) && Objects.equals(teamPosition, game.teamPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, summoner, gameURL, buffsStolen, dancedWithRiftHerald, skillshotsDodged, skillshotsHit, abilityUses, numberOfPings, deaths, kills, assists, gameDuration, championName, largestCriticalStrike, largestKillingSpree, largestMultiKill, teamPosition, timeCCingOthers, totalTimeSpentDead, win);
    }
}