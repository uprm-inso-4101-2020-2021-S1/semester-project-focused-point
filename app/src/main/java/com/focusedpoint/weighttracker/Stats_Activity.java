package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Stats_Activity extends AppCompatActivity {
    TextView StatContents;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        if(MainActivity.user==null){
            user=VisitorActivity.user;
        }else{
            user=MainActivity.user;
        }
        fillStatContents();
    }

    private void fillStatContents() {
        StatContents = findViewById(R.id.Stat_Contents);
        StatContents.setText(user.UserStatustoString());
    }
}