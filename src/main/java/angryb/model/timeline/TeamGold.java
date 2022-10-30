package angryb.model.timeline;

public class TeamGold {

    private int gold;
    private int timeStamp;

    public TeamGold(int gold, int timeStamp) {
        this.gold = gold;
        this.timeStamp = timeStamp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }
}