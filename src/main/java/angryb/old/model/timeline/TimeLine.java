package angryb.old.model.timeline;

import java.util.ArrayList;

public class TimeLine {

    private ArrayList<TeamGold> team1Gold;
    private ArrayList<TeamGold> team2Gold;
    private ArrayList<String> participants;

    public TimeLine() {
        this.team1Gold = new ArrayList<>();
        this.team2Gold = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public ArrayList<TeamGold> getTeam1GoldTimeLine() {
        return team1Gold;
    }

    public ArrayList<TeamGold> getTeam2GoldTimeLine() {
        return team2Gold;
    }

    public ArrayList<String> getParticipants() {
        return participants;
    }

    public ArrayList<Integer> getTeam1GoldArray(){
        ArrayList<Integer> output = new ArrayList<>();
        for (TeamGold gold : this.team1Gold){
            output.add(gold.getGold());
        }
        return output;
    }

    public ArrayList<Integer> getTeam2GoldArray(){
        ArrayList<Integer> output = new ArrayList<>();
        for (TeamGold gold : this.team2Gold){
            output.add(gold.getGold());
        }
        return output;
    }

    public ArrayList<Integer> getTeamGoldDifferenceArray(){
        ArrayList<Integer> output = new ArrayList<>();
        for (int i = 0; i < this.team2Gold.size(); i++){
            output.add(team1Gold.get(i).getGold()-team2Gold.get(i).getGold());
        }
        return output;
    }

    public void addTeam1Gold(int gold, int timeStamp){
        this.team1Gold.add(new TeamGold(gold, timeStamp));
    }

    public void addTeam2Gold(int gold, int timeStamp){
        this.team2Gold.add(new TeamGold(gold, timeStamp));
    }

    public void addPlayer(String puuid){
        this.participants.add(puuid);
    }
}
