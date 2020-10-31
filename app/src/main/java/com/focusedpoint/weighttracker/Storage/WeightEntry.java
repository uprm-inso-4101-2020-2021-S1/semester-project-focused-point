package com.focusedpoint.weighttracker.Storage;

import com.focusedpoint.weighttracker.DataStructures.list.ConvertableToString;

public class WeightEntry extends Date implements ConvertableToString {
    private int weight;

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

    public String toString(){
        return ""+weight+":"+getDate();
    }
}
