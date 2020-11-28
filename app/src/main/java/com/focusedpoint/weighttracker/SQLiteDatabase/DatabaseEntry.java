package com.focusedpoint.weighttracker.SQLiteDatabase;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.focusedpoint.weighttracker.User;
import com.focusedpoint.weighttracker.ui.login.LoginActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

//Stores all the information of a specific user.
public class DatabaseEntry {
    String username;
    String password;
    File UserFile;
    File VisitorFile;
    static User user;
    public DatabaseEntry(String username,String password, File UserFile){
        this.username=username;
        this.password=password;
        this.UserFile = UserFile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public File getUserFile() {
        return UserFile;
    }

    public void setUserFile(File userFile) {
        UserFile = userFile;
    }

    public File getVisitorFile() {
        return VisitorFile;
    }

    public void setVisitorFile(File visitorFile) {
        VisitorFile = visitorFile;
    }

    @Override
    public String toString() {
        String result = "";
        boolean foodsfound=false;
        try {
            Scanner scan = new Scanner(UserFile);
            //reads the contents of a file line by line
            while(scan.hasNextLine()) {
                String TempLine = scan.nextLine();
                result+=TempLine+"\n";
            }

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ e.toString());
        }
        return result;
    }
    public void readData(String data){
        user = new User();
        String result = "";
        try {


           File TempFile = new File(LoginActivity.context.getFilesDir(),"Temp.txt");
            FileOutputStream Temp = LoginActivity.context.openFileOutput("Temp.txt", Context.MODE_PRIVATE);
            FileOutputStream UserStream = LoginActivity.context.openFileOutput("UserData.txt", Context.MODE_PRIVATE);
            FileOutputStream VisitorStream = LoginActivity.context.openFileOutput("VisitorData.txt", Context.MODE_PRIVATE);
            Temp.write(data.getBytes());
            Temp.close();
           //InputStream inputStream = LoginActivity.context.openFileInput(TempFile);
            Scanner scan = new Scanner(TempFile);
            boolean foodsfound=false;
            boolean reset=false;

            //reads the contents of a file line by line
            while(scan.hasNextLine()) {
                String TempLine = scan.nextLine();
                  Log.println(Log.INFO,"debug","\n"+TempLine);
                //if the string TempLine contains "Visitor Code: "
                if(TempLine.contains("Visitor Code: ")){
                    user.setVisitorCode(Integer.parseInt(TempLine.substring(14,TempLine.length())));
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
                    if(sex.equals("MALE"))
                        user.setSex(User.sex.MALE);
                    if(sex.equals("FEMALE"))
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
                        Log.println(Log.INFO,"debug","\n"+TempLine);
                        //if the string TempLine contains "Foods with Calories:"
                        if(TempLine.contains("Foods with Calories:")|(foodsfound == true)){
                            foodsfound = true;
                            if(TempLine.contains("Foods with Calories:"))
                                if(!scan.hasNextLine()){
                                    UserStream.write(result.getBytes());
                                    UserStream.close();
                                    UserFile =  new File(LoginActivity.context.getFilesDir(),"UserData.txt");
                                    String VisitorData = "Username: "+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
                                    VisitorStream.write(VisitorData.getBytes());
                                    VisitorStream.close();
                                    VisitorFile =  new File(LoginActivity.context.getFilesDir(),"VisitorData.txt");
                                    Log.println(Log.INFO,"debug","\n"+result);
                                    result="";
                                    reset=true;
                                    break;
                                }
                                TempLine = scan.nextLine();
                            int location= TempLine.indexOf(":");
                            if(location==-1){
                                UserStream.write(result.getBytes());
                                UserStream.close();
                                UserFile =  new File(LoginActivity.context.getFilesDir(),"UserData.txt");
                                String VisitorData = "Username: "+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
                                VisitorStream.write(VisitorData.getBytes());
                                VisitorStream.close();
                                VisitorFile =  new File(LoginActivity.context.getFilesDir(),"VisitorData.txt");
                                Log.println(Log.INFO,"debug","\n"+result);
                                result="";
                                reset=true;
                                break;}
                            String NameOfFood = TempLine.substring(0,location);
                            String Calories = "";
                            Boolean foundCal= false;
                            Boolean foundEaten = false;
                            String TimeEaten ="";
                            com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String> TimesEaten = new com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String>(30);
                            for(int i = TempLine.indexOf("Calories");i<TempLine.length();i++){
                                Log.println(Log.INFO,"debug","BBBBBBBBBBBBBB: \n"+TempLine);
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
                                continue;
                            user.setWeight(Integer.parseInt(TempLine.substring(0,location)),TempLine.substring(location+1,TempLine.length()));
                            result += TempLine+"\n";
                        }
                    }

                }
                if(reset == false) {
                    result += TempLine+"\n";
                }else break;

            }
            if(reset == false){
                UserStream.write(result.getBytes());
                UserStream.close();
                UserFile =  new File(LoginActivity.context.getFilesDir(),"UserData.txt");
                String VisitorData = "Username: "+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
                VisitorStream.write(VisitorData.getBytes());
                VisitorStream.close();
                VisitorFile =  new File(LoginActivity.context.getFilesDir(),"VisitorData.txt");
                Log.println(Log.INFO,"debug","\n"+result);
                result="";
            }
        }


        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found:" + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ e.toString());
        }
    }
}
