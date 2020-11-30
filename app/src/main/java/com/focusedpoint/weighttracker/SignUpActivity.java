package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.focusedpoint.weighttracker.SQLiteDatabase.DatabaseEntry;
import com.focusedpoint.weighttracker.SQLiteDatabase.SQLite;
import com.focusedpoint.weighttracker.User;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    String userName;
    String password;
    String name;
    String gender;
    String age;
    String weight;
    String heightFT;
    String heightIN;
    static boolean SignOut= false;
    static User MainUser;
    SQLite myDataBase;
    Button signUp;
    static File UserFile;
    //File that contains the information regarding the user of the application
    static File VisitorFile;
    static String UserFileName = "UserData.txt";
    static String VisitorFileName = "VisitorData.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

    }

    // Defines what happens when back button is clicked;
    public void goback(View view) {
        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

    }

    // Defines what happens when user clicks on "Sign Up Button"
    public void createUser(View view) {
        userName = ((EditText)findViewById(R.id.username)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.password)).getText().toString().trim();
        name = ((EditText)findViewById(R.id.name)).getText().toString().trim();
        gender = ((EditText)findViewById(R.id.gender)).getText().toString().trim();
        age = ((EditText)findViewById(R.id.age)).getText().toString().trim();
        weight = ((EditText)findViewById(R.id.weight)).getText().toString().trim();
        heightFT = ((EditText)findViewById(R.id.Feet)).getText().toString().trim();
        heightIN = ((EditText)findViewById(R.id.inches)).getText().toString().trim();
        signUp = findViewById(R.id.button2);


        // Creates new user using all the info submitted by user upon sign up;
        try {
            if(userName.length()==0||password.length()==0||gender.length()==0||age.length()==0||weight.length()==0||heightFT.length()==0||heightIN.length()==0 || name.length()==0){
                Toast toast = Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            MainUser = new User(userName, password, User.sex.MALE, Integer.parseInt(age), Integer.parseInt(weight), Integer.parseInt(heightFT), Integer.parseInt(heightIN));
            MainUser.setName(name);
            WriteData(MainUser);
            //myDataBase.insertData(userName,password,new DatabaseEntry(MainUser.getUsername(),MainUser.getPassword(),UserFile,VisitorFile));
            userName = null;
            password = null;
            gender = null;
            age = null;
            weight = null;
            heightFT = null;
            heightIN =null;
            name = null;
            System.gc();
            // redirects user to Main Screen;
            startActivity(new Intent(SignUpActivity.this, AppMainScreen.class));
            finish();
        }catch (NullPointerException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_LONG);
            toast.show();
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(getApplicationContext(), "Please fill the fields (Age,Height,Weight) with numbers not characters", Toast.LENGTH_LONG);
            toast.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String WriteData(User user) throws IOException {
        if(UserFile!=null){
            UserFile.delete();
        }if(VisitorFile!=null){
            VisitorFile.delete();
        }
        String UserData = "Visitor Code: "+user.getVisitorCode()+ "\n" + "Name: " + user.getName() + "\n" + "Username: "+user.getUsername()+"\nPassword: "+user.getPassword()+"\nAge: "+user.getAge()+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
        // Log.println(Log.INFO,"debug","This is the data Written: "+UserData);
        String VisitorData = "Username: "+"Visitor"+"Visitor"+"\nPassword: "+"NONE"+"\nSex: "+user.getSex()+"\nHeight Feet: "+user.getHeightFT()+"\nHeight inches: " +user.getHeightIN()+"\nWeights with Dates:\n"+user.getWeights().toString()+"Foods with Calories:\n"+user.ht.toString();
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

    //Adds the new user to the Data Base;
    public void addUser(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isInserted =  myDataBase.insertData(userName,password,new DatabaseEntry(MainUser.getUsername(),MainUser.getPassword(),UserFile));
               if(isInserted = true){
                   //If the insertion on database was successful, it will display "User Created!" in the screen;
                   Toast.makeText(SignUpActivity.this,"User Created!", Toast.LENGTH_LONG).show();
               }
               else{
                   //If the insertion on database was successful, it will display "User Not Created!" in the screen;
                   Toast.makeText(SignUpActivity.this,"User Not Created!", Toast.LENGTH_LONG).show();
               }
            }
        });
    }

}