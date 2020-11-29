package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

import java.io.File;
import java.io.IOException;

public class AppMainScreen extends AppCompatActivity {

    TextView welcome;
    static User user;
    static File userFile;
    static File visitorFile;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.gc();
        setContentView(R.layout.activity_app_main_screen);

        if(SignUpActivity.MainUser==null){
            user = new User("admin","password", User.sex.MALE,40,180,6,5);//This user is a place holder. It is supposed to be provided by the login activity.
            }else{
            user =SignUpActivity.MainUser;
        }
        name();
    }

    // Defines what happens when user clicks on "Track My Weight" Button
    public void openWeight(View view) {
        startActivity(new Intent(AppMainScreen.this, MainActivity.class));

    }

    // Defines what happens when user clicks on "Body Mass Index"
    public void openBMI(View view) {
        startActivity(new Intent(AppMainScreen.this, BMIActivity.class));
    }

    // Defines what happens when user clicks on "Fat Mass Index"
    public void openFMI(View view) {
        startActivity(new Intent(AppMainScreen.this, FMIActivity.class));
    }

    // Defines what happens when user click on "Sign Out" button;
    public void signout(View view) {
        user.clear();
        try {
            userFile.delete();
            visitorFile.delete();
        }
        catch (NullPointerException e) {

        }


        startActivity(new Intent(AppMainScreen.this, LoginActivity.class));
        finish();
    }

    private void name() {
        name = "Welcome, " + user.getName();
        welcome = findViewById(R.id.welcome_User);
        welcome.setText(name);
    }
}