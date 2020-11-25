package com.focusedpoint.weighttracker;

import android.content.Context;
import android.os.Bundle;

import com.focusedpoint.weighttracker.DataStructures.hashtable.HashTableSC;
import com.focusedpoint.weighttracker.DataStructures.map.Map;
import com.focusedpoint.weighttracker.Storage.FoodEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class FoodTrackerActivity extends AppCompatActivity {

    Button TempButton;
    ConstraintLayout Layout;
    Integer Index;
    ArrayList<Button> FoodButtons;
    EditText FoodNameField;
    EditText CalorieField;
    Integer ButtonNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_tracker);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initializeButtons();
        Layout = findViewById(R.id.ConstraintLayout);
        Index =0;
        FoodNameField = ((EditText)findViewById(R.id.FoodNameField));
        CalorieField = ((EditText)findViewById(R.id.CalorieField));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.println(Log.INFO,"debug","The weights are:"+FoodNameField.getText().length()+"___"+CalorieField.getText());
                if(Index<=17) {
                    if(FoodNameField.getText()==null||CalorieField.getText()==null||FoodNameField.getText().length()==0||CalorieField.getText().length()==0){
                        Toast toast = Toast.makeText(getApplicationContext(), "Please add the name of the food and the amount of calories", Toast.LENGTH_LONG);
                        toast.show();
                    }else{
                        try {
                            addButton();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }else{
                    FoodNameField.setText("");
                    CalorieField.setText("");
                    Snackbar.make(view, "Maximum amount of foods present.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    }
                }

        });
        try {
            loadFoodData();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    //Method used to update the text present inside the buttons when starting the activity.
    private void loadFoodData() throws IOException {
        LoadDataHelper(MainActivity.UserFileName); //needs to be modified so that the visitor does not have this info.
        HashTableSC<String, FoodEntry> TempMap= (HashTableSC<String, FoodEntry>) MainActivity.user.ht;
        int size = TempMap.size();
        if(size==0){
            return;
        }
        com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String> Keys= (com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String>) TempMap.getKeys();
        for(Index = 0;Index<size;Index++) {
            Button b = FoodButtons.get(Index);
            String i=Keys.get(Index);
            b.setText(i + "\n" + TempMap.get(i).getCalories() + "\n" + TempMap.get(i).getTimesEaten().size());
            b.setVisibility(View.VISIBLE);
        }
    }

    private void initializeButtons() {
        FoodButtons = new ArrayList<Button>(18);
        FoodButtons.add((Button) findViewById(R.id.Food1));
        FoodButtons.add((Button) findViewById(R.id.Food2));
        FoodButtons.add((Button) findViewById(R.id.Food3));
        FoodButtons.add((Button) findViewById(R.id.Food4));
        FoodButtons.add((Button) findViewById(R.id.Food5));
        FoodButtons.add((Button) findViewById(R.id.Food6));
        FoodButtons.add((Button) findViewById(R.id.Food7));
        FoodButtons.add((Button) findViewById(R.id.Food8));
        FoodButtons.add((Button) findViewById(R.id.Food9));
        FoodButtons.add((Button) findViewById(R.id.Food10));
        FoodButtons.add((Button) findViewById(R.id.Food11));
        FoodButtons.add((Button) findViewById(R.id.Food12));
        FoodButtons.add((Button) findViewById(R.id.Food13));
        FoodButtons.add((Button) findViewById(R.id.Food14));
        FoodButtons.add((Button) findViewById(R.id.Food15));
        FoodButtons.add((Button) findViewById(R.id.Food16));
        FoodButtons.add((Button) findViewById(R.id.Food17));
        FoodButtons.add((Button) findViewById(R.id.Food18));
        for(Button b: FoodButtons){
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Button temp = (Button) v;
                   String s = (String)temp.getText();
                   String FoodName ="";
                   Integer NumberEaten = 0;
                   int count =0;
                   for(int i =0;i<s.length();i++){
                       if(s.charAt(i)=='\n'){
                           if(count==0){
                               FoodName= s.substring(0,i);
                               FoodEntry f = User.ht.get(FoodName);
                               if(f!=null){
                                f.addItemToEaten();
                                NumberEaten = f.getTimesEaten().size();
                               }
                           }
                           count++;
                           if(count>=2){
                               temp.setText(s.substring(0,i+1)+(NumberEaten));
                               break;
                           }

                       }
                   }
                    try {
                        WriteData(MainActivity.user);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public void addButton() throws IOException {
        Button b = FoodButtons.get(Index);
        b.setText(FoodNameField.getText()+"\n"+CalorieField.getText()+"\n"+1);
        MainActivity.user.addMeal(FoodNameField.getText().toString(),Double.parseDouble(CalorieField.getText().toString()));
        FoodNameField.setText("");
        CalorieField.setText("");
        b.setVisibility(View.VISIBLE);
        WriteData(MainActivity.user);
        Index++;
    }
    //Same method present in the main activity class but modified to fit the food tracking activity.
    public String WriteData(User user) throws IOException {
        String UserData = "Username: "+user.getUsername()+"\nPassword: "+user.getPassword()+"\nAge: "+user.getAge()+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
         Log.println(Log.INFO,"debug","This is the data Written: "+UserData);
        String VisitorData = "Username: "+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
        FileOutputStream UD = openFileOutput("UserData.txt", Context.MODE_PRIVATE);
        FileOutputStream VD = openFileOutput("VisitorData.txt",Context.MODE_PRIVATE);
        UD.write(UserData.getBytes());
        VD.write(VisitorData.getBytes());
        UD.close();
        VD.close();
        MainActivity.UserFile = new File(getFilesDir(),"UserData.txt");
        MainActivity.VisitorFile = new File(getFilesDir(),"VisitorData.txt");
        return UserData;
    }
    //Same method as Load Data in the main activity class but modified to fit the food tracking activity.
    public String LoadDataHelper(String FileName) throws IOException {
        String result = "";
        try{
            MainActivity.user.clear();
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
                //if the string TempLine contains "Username: "
                    if(TempLine.contains("Weights with Dates:")){
                    while(scan.hasNextLine()){
                        TempLine = scan.nextLine();
                        //if the string TempLine contains "Foods with Calories:"
                        if(TempLine.contains("Foods with Calories:")|(foodsfound == true)){
                            foodsfound = true;
                            if(TempLine.contains("Foods with Calories:"))
                                TempLine = scan.nextLine();
                            int location= TempLine.indexOf(":");
                            if(location==-1)
                                continue;
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
                                MainActivity.user.addMeal(NameOfFood,Float.valueOf(Calories),TimesEaten);
                        }
                        else{
                            int location= TempLine.indexOf(":");
                            if(location == -1)
                                break;
                            MainActivity.user.setWeight(Integer.parseInt(TempLine.substring(0,location)),TempLine.substring(location+1,TempLine.length()));
                        }}

                }

                result+=TempLine+"\n";

            }
        }


        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: "+FileName+" " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ FileName+" "+ e.toString());
        }
        if(FileName==MainActivity.UserFileName)
            MainActivity.UserFile = new File(getFilesDir(),FileName);
        else if(FileName==MainActivity.VisitorFileName)
            MainActivity.VisitorFile = new File(getFilesDir(),FileName);
        return result;
    }

}