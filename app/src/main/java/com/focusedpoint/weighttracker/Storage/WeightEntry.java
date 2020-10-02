package com.focusedpoint.weighttracker.Storage;

public class WeightEntry extends Date {
    public int weight;

    public WeightEntry(int weight, String date){
        this.weight = weight;
        this.Date =date;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
