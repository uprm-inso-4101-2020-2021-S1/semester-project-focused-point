package com.focusedpoint.weighttracker.Storage;

import com.focusedpoint.weighttracker.DataStructures.list.ArrayList;


public class FoodEntry extends Date {

    private double Calories;
    private ArrayList<String> TimesEaten; //List that contains the dates the food entry is eaten. It saves them in the following format (day/month/year).
    public  FoodEntry(double calories){
        this.Calories=calories;
        TimesEaten = new ArrayList<String>(30);
    }

    public void addItemToEaten(){
        TimesEaten.add(currentDate());
    }

    public double getCalories() {
        return Calories;
    }

    public void setCalories(double calories) {
        Calories = calories;
    }

    public ArrayList<String> getTimesEaten() {
        return TimesEaten;
    }

    public void setTimesEaten(ArrayList<String> timesEaten) {
        TimesEaten = timesEaten;
    }
}
