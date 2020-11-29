package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.focusedpoint.weighttracker.SQLiteDatabase.DatabaseEntry;
import com.focusedpoint.weighttracker.SQLiteDatabase.SQLite;
import com.focusedpoint.weighttracker.Storage.WeightEntry;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    public static User user;
    //Used to store data from the graph.
    LineGraphSeries<DataPoint> SeriesGraph;
    EditText NumberField;
    Button SubmitButton,GraphButton,FoodButton,StatButton, logIn;
    //List that contains all the weights of a person.
    // (Planing to make each index of the list represent a day of the month.)
    ArrayList<Integer> Weights = new ArrayList<Integer>(31);
    GraphView Graph;
    ArrayList<Button> Buttons;

    SharedPreferences SaveData;
    //File that contains the information regarding the user of the application
    static File UserFile;
    //File that contains the information regarding the user of the application

    static File VisitorFile;
    static String UserFileName = "UserData.txt";
    static String VisitorFileName = "VisitorData.txt";

    //DataBase
    SQLite myDataBase;


    double x,y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean loaded =false;
        if(SignUpActivity.MainUser==null){
        try {
            LoadUserData();
            loaded = true;
        } catch (IOException e) {
            e.printStackTrace();
            user = new User("admin","password", User.sex.MALE,40,180,6,5);//This user is a place holder. It is supposed to be provided by the login activity.
        }}else{
            user =SignUpActivity.MainUser;
        }
//        try {
//            Log.println(Log.INFO,"debug","This is the data written to file before DB:\n" + WriteData(user));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.println(Log.INFO,"debug","Entered the matrix");
//            DatabaseEntry DBE = new DatabaseEntry(user.getUsername(), user.getPassword(), UserFile);
//            DBE.readData(DBE.toString());
//        UserFile=DBE.getUserFile();
//        VisitorFile= DBE.getVisitorFile();

        NumberField = findViewById(R.id.NumberField);
        Graph = (GraphView) findViewById(R.id.graph);
        Graph.setVisibility(View.INVISIBLE);
        FoodButton = (Button) findViewById(R.id.button4);
        StatButton = (Button) findViewById(R.id.logo);
        logIn = (Button) findViewById(R.id.login);
        //sets the behavior of the graph button
        FoodButton.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View v) {
                                               try {
                                                   changeScreen();
                                                   finish();
                                               } catch (InterruptedException e) {
                                                   e.printStackTrace();
                                               }
                                           }
                                       });
        StatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeScreenToStats();
            }
        });
        enableSubmitButton();
        enableGraphButton();
        //DataBase instance
        myDataBase = new SQLite(this);
        System.gc();
    }

    private void changeScreen() throws InterruptedException {
        Thread.sleep(200);
        startActivity(new Intent(this, FoodTrackerActivity.class));
    }

    private void changeScreenToStats() {
        System.gc();
        startActivity(new Intent(this, Stats_Activity.class));
    }

    private void enableGraphButton() {
        GraphButton = (Button) findViewById(R.id.GraphButton);
        //sets the behavior of the graph button
        GraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //creates a graph object that stores the data of graph view.
            SeriesGraph = new LineGraphSeries<DataPoint>();
            for(int i=0;i<user.getWeights().size();i++){
            //adds a new point to the graph object
            SeriesGraph.appendData(new DataPoint(i+1,user.weightAt(i+1)),false,31);
            SeriesGraph.setDrawDataPoints(true);
            }
            //adds the data stored in SeriesGraph to the GraphView so that it can draw the graph.
            //SeriesGraph needs more than 3 data points so that the graph can display correctly.
            Graph.addSeries(SeriesGraph);
            if(user.getWeights().size()>=5){
            Graph.setVisibility(View.VISIBLE);
                try {
                    Log.println(Log.INFO,"debug","The data written is\n"+WriteData(user));

            } catch (Exception e) {
                    e.printStackTrace();
                }}
            else {
                Toast toast = Toast.makeText(getApplicationContext(), "To graph weights more than 5 are needed", Toast.LENGTH_LONG);
                toast.show();
            }
            }
        });
    }

    private void enableSubmitButton() {
        SubmitButton = (Button) findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInputToList();
            }});
    }

    //This function adds elements to the list titled weights.
    // The elements it adds is the number written inside the text box named "NumberField".
    public void addInputToList(){
        if(NumberField.getText()==null)
            return;
        else {
            //gets the text inside NumberField and removes all the whitespace inside it
            String NumericalInput = NumberField.getText().toString().trim();
            //checks if the text can be converted to a Double.
            // If so, it adds it to weights and sets the text inside NumberField to empty.
            try {
                user.setWeight(Integer.parseInt(NumericalInput));
                NumberField.setText("");
                String debug = ArrayListToString(user.getWeights());
                Log.println(Log.INFO,"debug","The weights are:"+debug);
                WriteData(user);
            }
            //if an error occurs, then the text inside NumberField is not a valid weight
            catch (NumberFormatException | IOException e){
            NumberField.setText("");
//            user.setWeights(new com.focusedpoint.weighttracker.DataStructures.list.ArrayList<WeightEntry>(10));
            Log.println(Log.INFO,"debug","Not a valid weight");
            }
        }
    }
    //Turns an arrayList of doubles into a single string
    public String ArrayListToString(com.focusedpoint.weighttracker.DataStructures.list.ArrayList<WeightEntry> list){
        String Temp ="";
        int count = 0;
        for(WeightEntry s:list) {
            count++;
            if(count==1)
                Temp = ""+s.toString();
            else
            Temp = Temp + "," + s.toString();//separates each element of the list by a comma.
        }
        return Temp;
    }


public String WriteData(User user) throws IOException {
        if(UserFile!=null){
            UserFile.delete();
        }if(VisitorFile!=null){
        VisitorFile.delete();
    }
    String UserData = "Visitor Code: "+user.getVisitorCode()+ "\n" + "Name: " + user.getName() + "\n" + "Username: "+user.getUsername()+"\nPassword: "+user.getPassword()+"\nAge: "+user.getAge()+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
   // Log.println(Log.INFO,"debug","This is the data Written: "+UserData);
    String VisitorData = "Username: "+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
    FileOutputStream UD = openFileOutput("UserData.txt",Context.MODE_PRIVATE);
    FileOutputStream VD = openFileOutput("VisitorData.txt",Context.MODE_PRIVATE);
    UD.write(UserData.getBytes());
    VD.write(VisitorData.getBytes());
    UD.close();
    VD.close();
    UserFile = new File(getFilesDir(),"UserData.txt");
    VisitorFile = new File(getFilesDir(),"VisitorData.txt");
    return UserData;
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
                            if(TempLine.contains("Foods with Calories:"))
                            TempLine = scan.nextLine();
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
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ FileName+" "+ e.toString());
        }
        if(FileName==UserFileName)
            UserFile = new File(getFilesDir(),FileName);
        else if(FileName==VisitorFileName)
            VisitorFile = new File(getFilesDir(),FileName);
        return result;
    }



    public void LoadUserData() throws IOException {
        LoadDataHelper(UserFileName);
    }

    public void LoadVisitorData() throws IOException {
        LoadDataHelper(VisitorFileName);
    }

    //Action on login button
    public void logInAction(){
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
        });
    }

}