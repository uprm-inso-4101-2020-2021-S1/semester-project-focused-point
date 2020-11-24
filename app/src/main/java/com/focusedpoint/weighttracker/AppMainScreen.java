package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.focusedpoint.weighttracker.ui.login.LoginActivity;

public class AppMainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_screen);


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
}