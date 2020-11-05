package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmi);
    }

    //defines what to do when back button is clicked on; it will return user to main screen
    public void backtoMain(View view) {
        startActivity(new Intent(FMIActivity.this, AppMainScreen.class));
    }
}