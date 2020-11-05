package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BMIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);
    }

    // defines what happens when back button is pressed; user will be returned to main screen
    public void backtoMain(View view) {
        startActivity(new Intent(BMIActivity.this, AppMainScreen.class));
    }
}