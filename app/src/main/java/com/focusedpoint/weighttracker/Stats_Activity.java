package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Stats_Activity extends AppCompatActivity {
    TextView StatContents;
    TextView VisitorCode;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        if(MainActivity.user==null){
            user=VisitorActivity.user;
        }else{
            user=MainActivity.user;
        }
        fillStatContents();
    }

    private void fillStatContents() {
        StatContents = findViewById(R.id.Stat_Contents);
        StatContents.setText(user.UserStatustoString());
        VisitorCode = findViewById(R.id.VC);
        VisitorCode.setText(""+user.getVisitorCode());

    }

    //Method responsible for loading the data present in a file of the specified name.
    // It currently supports (UserFileName,VisitorFileName).
    //To add support for another file
    public String LoadDataHelper(String FileName) throws IOException {
        String result = "";
        try{
            user.clear();
        }catch (NullPointerException e){
            throw new IOException();
        }

        boolean foodsfound=false;
        try {
            InputStream inputStream = openFileInput(FileName);
            Scanner scan = new Scanner(inputStream);
            //reads the contents of a file line by line
            while(scan.hasNextLine()) {
                String TempLine = scan.nextLine();
                Log.println(Log.INFO,"debug","\n"+TempLine);
                //if the string TempLine contains "Visitor Code: "
                if(TempLine.contains("Visitor Code: ")){
                    user.setVisitorCode(Integer.parseInt(TempLine.substring(14,TempLine.length())));
                }
                else if(TempLine.contains("Name: ")){
                    user.setName(TempLine.substring(6,TempLine.length()));
                }
                //if the string TempLine contains "Username:
                else if(TempLine.contains("Username: ")){
                    user.setUsername(TempLine.substring(10,TempLine.length()));
                }
                //if the string TempLine contains "Password: "
                else if(TempLine.contains("Password: ")){
                    user.setPassword(TempLine.substring(10,TempLine.length()));
                }
                //if the string TempLine contains "Age: "
                else if(TempLine.contains("Age: ")){
                    user.setAge(Integer.valueOf(TempLine.substring(5,TempLine.length())));
                }
                //if the string TempLine contains "Sex: "
                else if(TempLine.contains("Sex: ")){
                    String sex = TempLine.substring(5,TempLine.length());
                    if(sex == "MALE")
                        user.setSex(User.sex.MALE);
                    if(sex == "FEMALE")
                        user.setSex(User.sex.FEMALE);
                }
                //if the string TempLine contains "Height Feet: "
                else if(TempLine.contains("Height Feet: "))
                    user.setHeightFT(Integer.parseInt(TempLine.substring(13,TempLine.length())));
                    //if the string TempLine contains "Height inches: "
                else if(TempLine.contains("Height inches: "))
                    user.setHeightIN(Integer.parseInt(TempLine.substring(15,TempLine.length())));
                    //if the string TempLine contains "Weights with Dates:"
                else if(TempLine.contains("Weights with Dates:")){
                    while(scan.hasNextLine()){
                        TempLine = scan.nextLine();
                        //if the string TempLine contains "Foods with Calories:"
                        if(TempLine.contains("Foods with Calories:")|(foodsfound == true)){
                            foodsfound = true;
                            if(TempLine.contains("Foods with Calories:")){
                                if(scan.hasNextLine())
                                    TempLine = scan.nextLine();
                                else break;
                            }
                            int location= TempLine.indexOf(":");
                            if(location==-1)
                                break;
                            String NameOfFood = TempLine.substring(0,location);
                            String Calories = "";
                            Boolean foundCal= false;
                            Boolean foundEaten = false;
                            String TimeEaten ="";
                            com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String> TimesEaten = new com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String>(30);
                            for(int i = TempLine.indexOf("Calories");i<TempLine.length();i++){
                                if(TempLine.charAt(i)=='('){
                                    foundCal = true;
                                    i++;
                                }
                                if(foundCal==true){
                                    if(TempLine.charAt(i)==')'){
                                        foundCal=false;
                                    }else{
                                        Calories=Calories+TempLine.charAt(i);
                                    }

                                }
                                if(TempLine.charAt(i)=='['){
                                    foundEaten=true;
                                    i++;
                                }
                                if(foundEaten==true){
                                    if(TempLine.charAt(i)==']'){
                                        foundCal=false;
                                    }else if(!(TempLine.charAt(i)==',')){
                                        TimeEaten=TimeEaten+TempLine.charAt(i);
                                    }else{
                                        TimesEaten.add(TimeEaten);
                                        TimeEaten="";
                                    }

                                }
                            }
                            if(!(Calories==""))
                                user.addMeal(NameOfFood,Float.valueOf(Calories),TimesEaten);
                        }
                        else{
                            int location= TempLine.indexOf(":");
                            if(location == -1)
                                break;
                            user.setWeight(Integer.parseInt(TempLine.substring(0,location)),TempLine.substring(location+1,TempLine.length()));
                        }}

                }

                result+=TempLine+"\n";

            }
        }


        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: "+FileName+" " + e.toString());
            return "";
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ FileName+" "+ e.toString());
        }

        return result;
    }
}