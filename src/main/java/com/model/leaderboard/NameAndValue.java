package com.model.leaderboard;

public class NameAndValue {

    String name;
    int value;
    int maxValue;

    public NameAndValue(String name, int value, int maxValue) {
        this.name = name;
        this.value = value;
        this.maxValue = maxValue;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }
}
