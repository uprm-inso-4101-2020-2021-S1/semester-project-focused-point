package com.focusedpoint.weighttracker.SQLiteDatabase;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.focusedpoint.weighttracker.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

//Stores all the information of a specific user.
public class DatabaseEntry extends AppCompatActivity {
    String username;
    String password;
    File UserFile;
    File VisitorFile;
    public DatabaseEntry(String username,String password, File UserFile, File VisitorFile){
        this.username=username;
        this.password=password;
        this.VisitorFile=VisitorFile;
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
                result+=TempLine;
            }
            result+=".-.\n";

            scan = new Scanner(VisitorFile);
            //reads the contents of a file line by line
            while(scan.hasNextLine()) {
                String TempLine = scan.nextLine();
                result+=TempLine;
            }

        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: "+ e.toString());
        }
        return result;
    }
//    public void readData(){
//        String result = "";
//        boolean foodsfound=false;
//        try {
//            InputStream inputStream = openFileInput(FileName);
//            Scanner scan = new Scanner(inputStream);
//            //reads the contents of a file line by line
//            while(scan.hasNextLine()) {
//                String TempLine = scan.nextLine();
//                Log.println(Log.INFO,"debug","\n"+TempLine);
//                //if the string TempLine contains "Visitor Code: "
//                if(TempLine.contains("Visitor Code: ")){
//                    user.setVisitorCode(Integer.parseInt(TempLine.substring(14,TempLine.length())));
//                }
//                //if the string TempLine contains "Username:
//                else if(TempLine.contains("Username: ")){
//                    user.setUsername(TempLine.substring(10,TempLine.length()));
//                }
//                //if the string TempLine contains "Password: "
//                else if(TempLine.contains("Password: ")){
//                    user.setPassword(TempLine.substring(10,TempLine.length()));
//                }
//                //if the string TempLine contains "Age: "
//                else if(TempLine.contains("Age: ")){
//                    user.setAge(Integer.valueOf(TempLine.substring(5,TempLine.length())));
//                }
//                //if the string TempLine contains "Sex: "
//                else if(TempLine.contains("Sex: ")){
//                    String sex = TempLine.substring(5,TempLine.length());
//                    if(sex == "MALE")
//                        user.setSex(User.sex.MALE);
//                    if(sex == "FEMALE")
//                        user.setSex(User.sex.FEMALE);
//                }
//                //if the string TempLine contains "Height Feet: "
//                else if(TempLine.contains("Height Feet: "))
//                    user.setHeightFT(Integer.parseInt(TempLine.substring(13,TempLine.length())));
//                    //if the string TempLine contains "Height inches: "
//                else if(TempLine.contains("Height inches: "))
//                    user.setHeightIN(Integer.parseInt(TempLine.substring(15,TempLine.length())));
//                    //if the string TempLine contains "Weights with Dates:"
//                else if(TempLine.contains("Weights with Dates:")){
//                    while(scan.hasNextLine()){
//                        TempLine = scan.nextLine();
//                        //if the string TempLine contains "Foods with Calories:"
//                        if(TempLine.contains("Foods with Calories:")|(foodsfound == true)){
//                            foodsfound = true;
//                            if(TempLine.contains("Foods with Calories:"))
//                                TempLine = scan.nextLine();
//                            int location= TempLine.indexOf(":");
//                            if(location==-1)
//                                break;
//                            String NameOfFood = TempLine.substring(0,location);
//                            String Calories = "";
//                            Boolean foundCal= false;
//                            Boolean foundEaten = false;
//                            String TimeEaten ="";
//                            com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String> TimesEaten = new com.focusedpoint.weighttracker.DataStructures.list.ArrayList<String>(30);
//                            for(int i = TempLine.indexOf("Calories");i<TempLine.length();i++){
//                                if(TempLine.charAt(i)=='('){
//                                    foundCal = true;
//                                    i++;
//                                }
//                                if(foundCal==true){
//                                    if(TempLine.charAt(i)==')'){
//                                        foundCal=false;
//                                    }else{
//                                        Calories=Calories+TempLine.charAt(i);
//                                    }
//
//                                }
//                                if(TempLine.charAt(i)=='['){
//                                    foundEaten=true;
//                                    i++;
//                                }
//                                if(foundEaten==true){
//                                    if(TempLine.charAt(i)==']'){
//                                        foundCal=false;
//                                    }else if(!(TempLine.charAt(i)==',')){
//                                        TimeEaten=TimeEaten+TempLine.charAt(i);
//                                    }else{
//                                        TimesEaten.add(TimeEaten);
//                                        TimeEaten="";
//                                    }
//
//                                }
//                            }
//                            if(!(Calories==""))
//                                user.addMeal(NameOfFood,Float.valueOf(Calories),TimesEaten);
//                        }
//                        else{
//                            int location= TempLine.indexOf(":");
//                            if(location == -1)
//                                break;
//                            user.setWeight(Integer.parseInt(TempLine.substring(0,location)),TempLine.substring(location+1,TempLine.length()));
//                        }}
//
//                }
//
//                result+=TempLine+"\n";
//
//            }
//        }
//
//
//        catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: "+FileName+" " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: "+ FileName+" "+ e.toString());
//        }
//        if(FileName==UserFileName)
//            UserFile = new File(getFilesDir(),FileName);
//        else if(FileName==VisitorFileName)
//            VisitorFile = new File(getFilesDir(),FileName);
//        return result;
//    }
}
