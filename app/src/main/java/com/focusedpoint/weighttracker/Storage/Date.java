package com.focusedpoint.weighttracker.Storage;

import com.focusedpoint.weighttracker.DataStructures.list.ConvertableToString;

import java.text.SimpleDateFormat;

public class Date implements ConvertableToString {
    //----------------Object Specific Methods and Variables-------------------------------
    public String Date = "";//the date has the following format. day/month/year
    private String Day = "";
    private String Month = "";
    private String Year = "";

    //changes the date
    public void setDate(String date){
        this.Date = date;
        this.Day = "";
        this.Month = "";
        this.Year = "";
    }

    public String getDate(){
        return Date;
    }

    public  String getYear(String date){
        if(Year.equals(""))
            Year = date.substring(6,date.length());
        return Year;
    }

    public String getMonth(String date){
        if(Month.equals(""))
            Month = date.substring(3,5);
        return Month;
    }

    public String getDay(String date){
        if(Day.equals(""))
            Day = date.substring(0,2);
        return Day;
    }

    //----------------Universal Methods and Variables-------------------------------
    public static String currentDate() {
        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        return date.format(new java.util.Date());
    }

    @Override
    public String toString() {
        return "Date{" +
                "Date='" + Date + '\'' +
                ", Day='" + Day + '\'' +
                ", Month='" + Month + '\'' +
                ", Year='" + Year + '\'' +
                '}';
    }
}
