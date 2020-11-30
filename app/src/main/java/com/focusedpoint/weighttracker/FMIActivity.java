package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class FMIActivity extends AppCompatActivity {

    TextView fmiPercentage;
    User user;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fmi);

        if(AppMainScreen.user==null){
            user = new User("admin","password", User.sex.MALE,40,180,6,5);//This user is a place holder. It is supposed to be provided by the login activity.
        }else{
            user = AppMainScreen.user;
        }

        fillFMIPercentage();
    }

    //defines what to do when back button is clicked on; it will return user to main screen
    public void backtoMain(View view) {
        startActivity(new Intent(FMIActivity.this, AppMainScreen.class));
    }

    private void fillFMIPercentage() {
        try{
            message = "Currently: " + Double.toString(Math.round(user.BFP())) + "%";
        }catch (NullPointerException e){
            message = "Currently: " + "20" + "%";
        }
        fmiPercentage = findViewById(R.id.fmiPercentage);
        fmiPercentage.setText(message);
    }
}