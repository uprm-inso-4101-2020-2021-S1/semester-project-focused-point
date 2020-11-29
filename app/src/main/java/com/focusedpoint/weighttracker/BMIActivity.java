package com.focusedpoint.weighttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.focusedpoint.weighttracker.User;

public class BMIActivity extends AppCompatActivity {

    TextView bmiPercentage;
    User user;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_m_i);

        if(AppMainScreen.user==null){
            user = new User("admin","password", User.sex.MALE,40,180,6,5);//This user is a place holder. It is supposed to be provided by the login activity.
        }else{
            user = AppMainScreen.user;
        }

        fillBMI();
    }

    // defines what happens when back button is pressed; user will be returned to main screen
    public void backtoMain(View view) {
        startActivity(new Intent(BMIActivity.this, AppMainScreen.class));
    }

    private void fillBMI() {
        message = "Currently: " + Double.toString(user.BMI()) + " (" + user.classifyBMI() + ")";
        bmiPercentage = findViewById(R.id.bmi_percentage);
        bmiPercentage.setText(message);
    }

}