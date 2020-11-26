package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Stats_Activity extends AppCompatActivity {
    TextView StatContents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        fillStatContents();
    }

    private void fillStatContents() {
        StatContents = findViewById(R.id.Stat_Contents);
        StatContents.setText(MainActivity.user.UserStatustoString());
    }
}