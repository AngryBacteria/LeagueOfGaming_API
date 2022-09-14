package com.model.leaderboard;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

public class Leaderboard {

    List<NameAndValue> nameAndValue;
    String leaderBoardName;
    int maxValue;


    public Leaderboard(List<NameAndValue> nameAndValue, String leaderBoardName, int maxValue) {
        this.nameAndValue = nameAndValue;
        this.leaderBoardName = leaderBoardName;
        this.maxValue = maxValue;
    }

    public List<NameAndValue> getNameAndValue() {
        return nameAndValue;
    }

    public void setNameAndValue(List<NameAndValue> nameAndValue) {
        this.nameAndValue = nameAndValue;
    }

    public String getLeaderBoardName() {
        return leaderBoardName;
    }

    public void setLeaderBoardName(String leaderBoardName) {
        this.leaderBoardName = leaderBoardName;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
