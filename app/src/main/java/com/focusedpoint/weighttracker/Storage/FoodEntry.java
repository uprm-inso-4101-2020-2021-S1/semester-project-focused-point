package com.focusedpoint.weighttracker.Storage;

import com.focusedpoint.weighttracker.DataStructures.list.ArrayList;
import com.focusedpoint.weighttracker.DataStructures.list.ConvertableToString;


public class FoodEntry extends Date implements ConvertableToString {

    private double Calories;
    private ArrayList<String> TimesEaten; //List that contains the dates the food entry is eaten. It saves them in the following format (day/month/year).
    public  FoodEntry(double calories){
        this.Calories=calories;
        TimesEaten = new ArrayList<String>(30);
        addItemToEaten();

    }
    public  FoodEntry(double calories,ArrayList<String> TimesEaten){
        this.Calories=calories;
        this.TimesEaten = TimesEaten;

    }

    public void addItemToEaten(){
        TimesEaten.add(currentDate());
    }

    public void addItemToEaten(String TimeEaten){
        TimesEaten.add(TimeEaten);
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

    @Override
    public String toString() {
        String result = "Calories("+Calories+")"+"TimesEaten[";
        for(String s:TimesEaten){
            if(s!=null)
                result=result+s+",";
        }
        result+="]\n";
        return result;
    }
}
